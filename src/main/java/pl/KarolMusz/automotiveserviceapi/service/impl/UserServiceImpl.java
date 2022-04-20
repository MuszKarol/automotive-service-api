package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.*;
import pl.KarolMusz.automotiveserviceapi.mapper.CarMapper;
import pl.KarolMusz.automotiveserviceapi.mapper.UserMapper;
import pl.KarolMusz.automotiveserviceapi.model.*;
import pl.KarolMusz.automotiveserviceapi.model.enums.Role;
import pl.KarolMusz.automotiveserviceapi.repository.*;
import pl.KarolMusz.automotiveserviceapi.service.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final ContactDetailsRepository contactDetailsRepository;
    private final CarRepository carRepository;
    private final ModelRepository modelRepository;
    private final UserMapper userMapper;
    private final CarMapper carMapper;

    @Override
    public UserDTO getUser() {
        //TODO
        return null;
    }

    @Override
    public UserDTO getUserById(UUID uuid) throws Exception {
        Optional<User> userOptional = userRepository.findById(uuid);

        if (userOptional.isEmpty())
            throw new Exception();  //TODO

        User user = userOptional.get();

        return getUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserCreateRequestDTO userDTO) throws Exception {
        Optional<User> userOptional = userRepository.getUserByEmail(userDTO.email);

        if (userOptional.isPresent())
            throw new Exception();

        User user = User.builder()
                .email(userDTO.email)
                .name(userDTO.name)
                .surname(userDTO.surname)
                .role(Role.valueOf(userDTO.role))
                .address(getAddress(userDTO.address))
                .contactDetails(getContactDetails(userDTO.contactDetails))
                .listOfCars(new ArrayList<>())
                .build();

        return getUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO updateUserDetails(UserDTO userDTO) throws Exception {
        Optional<User> userOptional = userRepository.getUserByEmail(userDTO.email);

        if (userOptional.isEmpty())
            throw new Exception();  //TODO

        User user = userOptional.get();

        user.setEmail(userDTO.email);
        user.setName(userDTO.name);
        user.setSurname(userDTO.surname);
        user.setRole(Role.valueOf(userDTO.role));
        user.setAddress(getAddress(userDTO.address));
        user.setContactDetails(getContactDetails(userDTO.contactDetails));

        return getUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO addUserVehicle(UUID userId, CarDTO carDTO) throws Exception {
        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty()) {
            throw new Exception();  //TODO
        }

        User user = userOptional.get();

        if(carRepository.getCarByVinCode(carDTO.vinCode).isPresent()) {
            throw new Exception();
        }

        Optional<Car> carOptional = getCar(carDTO);

        if (carOptional.isEmpty()){
            throw new Exception(); //TODO
        }

        user.getListOfCars().add(carRepository.save(carOptional.get()));

        return getUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO deleteUserVehicle(UUID userId, String vin) throws Exception {
        Optional<Car> carOptional = carRepository.getCarByVinCode(vin);

        if (carOptional.isEmpty())
            throw new Exception();  //TODO

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new Exception();  //TODO

        User user = userOptional.get();
        Car car = carOptional.get();

        user.getListOfCars().remove(carOptional.get());
        userRepository.save(user);
        carRepository.delete(car);

        return getUserDTO(user);
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

    private Optional<Car> getCar(CarDTO carDTO) {
        Optional<Car> carOptional;
        Optional<Model> modelOptional = modelRepository.getModelByBrandNameAndModelName(carDTO.brandName, carDTO.modelName);

        Model model;

        if (modelOptional.isEmpty()) {
            model = Model.builder()
                    .brandName(carDTO.brandName)
                    .modelName(carDTO.modelName)
                    .build();

            modelRepository.save(model);
        }
        else {
            model = modelOptional.get();
        }

        carOptional = carRepository.getCarByVinCodeAndModel(carDTO.vinCode, model);

        if (carOptional.isEmpty()) {
            Car car = Car.builder()
                    .model(model)
                    .licensePlate(carDTO.licensePlate)
                    .vinCode(carDTO.vinCode)
                    .build();

            carOptional = Optional.of(car);
        }

        return carOptional;
    }

}
