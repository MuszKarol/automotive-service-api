package pl.KarolMusz.automotiveserviceapi.service.impl;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.*;
import pl.KarolMusz.automotiveserviceapi.mapper.CarMapper;
import pl.KarolMusz.automotiveserviceapi.mapper.UserMapper;
import pl.KarolMusz.automotiveserviceapi.model.*;
import pl.KarolMusz.automotiveserviceapi.model.enums.Role;
import pl.KarolMusz.automotiveserviceapi.repository.*;
import pl.KarolMusz.automotiveserviceapi.service.UserService;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ContactDetailsRepository contactDetailsRepository;
    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final UserMapper userMapper;
    private final CarMapper carMapper;
    private final BCryptPasswordEncoder passwordEncoder;

    public static User getUserFromContext(UserRepository userRepository) throws UsernameNotFoundException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        Optional<User> user = userRepository.getUserByEmail(email);

        if (user.isEmpty() || !user.get().getEmail().equals(email)) {
            throw new UsernameNotFoundException("User not found");
        }

        return user.get();
    }

    @Override
    public UserDTO getUser() throws UsernameNotFoundException {
        return getUserDTO(getUserFromContext(this.userRepository));
    }

    @Override
    public UserDTO getUserById(UUID uuid) throws EntityNotFoundException {
        Optional<User> userOptional = userRepository.findById(uuid);

        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("User not found");
        }

        User user = userOptional.get();

        return getUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserCreateRequestDTO userDTO) throws EntityExistsException {
        Optional<User> userOptional = userRepository.getUserByEmail(userDTO.email);

        if (userOptional.isPresent()) {
            throw new EntityExistsException("User already exists");
        }

        User user = User.builder()
                .email(userDTO.email)
                .name(userDTO.name)
                .password(passwordEncoder.encode(userDTO.password))
                .surname(userDTO.surname)
                .role(Role.valueOf(userDTO.role))
                .address(getAddress(userDTO.address))
                .contactDetails(getContactDetails(userDTO.contactDetails))
                .listOfCars(new ArrayList<>())
                .build();

        return getUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUserDetails(UserDTO userDTO) throws UsernameNotFoundException {
        User user = getUserFromContext(userRepository);

        user.setEmail(userDTO.email);
        user.setName(userDTO.name);
        user.setSurname(userDTO.surname);
        user.setRole(Role.valueOf(userDTO.role));
        user.setAddress(getAddress(userDTO.address));
        user.setContactDetails(getContactDetails(userDTO.contactDetails));

        return getUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO addUserVehicle(CarDTO carDTO) throws UsernameNotFoundException, EntityExistsException {
        User user = getUserFromContext(userRepository);
        Optional<Car> carOptional = findUserCar(user, carDTO.vinCode);

        if (carOptional.isPresent()) {
            throw new EntityExistsException("Car already exists");
        }

        Car car = getCar(carDTO);
        user.getListOfCars().add(carRepository.save(car));

        return getUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO deleteUserVehicle(String vin) throws EntityNotFoundException {
        User user = getUserFromContext(userRepository);

        Optional<Car> carOptional = findUserCar(user, vin);

        if (carOptional.isEmpty()) {
            throw new EntityNotFoundException("Car not found");
        }

        Car car = carOptional.get();

        user.getListOfCars().remove(car);
        userRepository.save(user);

        return getUserDTO(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.getUserByEmail(email);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("No user found with the submitted e-mail address");
        }

        return userOptional.get();
    }

    public String createAuthenticationJsonObjectAsString(String userEmail, String token) {
        Optional<User> userOptional = userRepository.getUserByEmail(userEmail);

        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("No user found with the submitted e-mail address");
        }

        return new Gson().toJson(userMapper.userToAuthenticationRequestDTO(userOptional.get(), token));
    }

    private Optional<Car> findUserCar(User user, String vin) {
        return user.getListOfCars().stream()
                .filter(car -> car.getVinCode().equals(vin))
                .findFirst();
    }

    private Address getAddress(AddressDTO addressDTO) {
        Address address = addressRepository
                .getAddressByBuildingNumberAndStreetAndCityAndPostalCodeAndCountry(
                        addressDTO.buildingNumber,
                        addressDTO.street,
                        addressDTO.city,
                        addressDTO.postalCode,
                        addressDTO.country)
                .orElseGet(() -> Address.builder()
                        .buildingNumber(addressDTO.buildingNumber)
                        .street(addressDTO.street)
                        .city(addressDTO.city)
                        .postalCode(addressDTO.postalCode)
                        .country(addressDTO.country)
                        .build());

        return addressRepository.save(address);
    }

    private ContactDetails getContactDetails(ContactDTO contactDTO) {
        ContactDetails contactDetails = contactDetailsRepository
                .getContactDetailsByPhoneNumberAndSecondEmail(contactDTO.phoneNumber, contactDTO.secondEmail)
                .orElseGet(() -> ContactDetails.builder()
                        .phoneNumber(contactDTO.phoneNumber)
                        .secondEmail(contactDTO.secondEmail)
                        .build()
                );

        return contactDetailsRepository.save(contactDetails);
    }

    private List<CarDTO> getCarDTOs(User user) {
        return user.getListOfCars().stream()
                .map(carMapper::carToCarDTO)
                .toList();
    }

    private UserDTO getUserDTO(User user) {
        return userMapper.userToUserDTO(user,
                userMapper.addressToAddressDTO(user.getAddress()),
                userMapper.contactToContactDTO(user.getContactDetails()),
                getCarDTOs(user));
    }

    private Car getCar(CarDTO carDTO) {
        Model model = getModel(carDTO);

        return Car.builder()
                .model(model)
                .licensePlate(carDTO.licensePlate)
                .vinCode(carDTO.vinCode)
                .carRegistrationDate(carDTO.carRegistrationDate)
                .version(carDTO.version)
                .engine(carDTO.engine)
                .build();
    }

    private Model getModel(CarDTO carDTO) {
        Optional<Model> modelOptional = modelRepository.getModelByBrandNameAndModelName(
                carDTO.brandName,
                carDTO.modelName
        );
        Model model;

        if (modelOptional.isEmpty()) {
            model = Model.builder()
                    .brandName(carDTO.brandName)
                    .modelName(carDTO.modelName)
                    .build();

            modelRepository.save(model);
        } else {
            model = modelOptional.get();
        }

        return model;
    }
}
