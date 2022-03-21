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
    private final BrandRepository brandRepository;
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

        List<Car> cars = getCars(userDTO);

        User user = User.builder()
                .email(userDTO.email)
                .name(userDTO.name)
                .surname(userDTO.surname)
                .role(Role.valueOf(userDTO.role))
                .address(getAddress(userDTO.address))
                .contactDetails(getContactDetails(userDTO.contactDetails))
                .listOfCars(cars)
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

        if (userOptional.isEmpty())
            throw new Exception();  //TODO

        User user = userOptional.get();

        Optional<Car> carOptional = getCar(carDTO);

        if (carOptional.isEmpty())
            throw new Exception(); //TODO

        user.getListOfCars().add(carOptional.get());

        return getUserDTO(userRepository.save(user));
    }

    @Override
    public UserDTO deleteUserVehicle(UUID userId, UUID carId) throws Exception {
        Optional<Car> carOptional = carRepository.findById(carId);

        if (carOptional.isEmpty())
            throw new Exception();  //TODO

        carRepository.delete(carOptional.get());

        Optional<User> userOptional = userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new Exception();  //TODO

        return getUserDTO(userOptional.get());
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

    private List<Car> getCars(UserCreateRequestDTO userDTO) {
        return userDTO.carDTOList.stream()
                .map(this::getCar)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    //TODO
    private Optional<Car> getCar(CarDTO carDTO) {
        Optional<Car> carOptional = Optional.empty();

        Optional<Brand> brandOptional = brandRepository.getBrandByName(carDTO.brandName);

        if (brandOptional.isEmpty())
            return carOptional;

        Optional<Model> modelOptional = modelRepository.getModelByNameAndBrand(carDTO.modelName, brandOptional.get());

        if (modelOptional.isEmpty())
            return carOptional;

        carOptional = carRepository.getCarByVinCodeAndModel(carDTO.vinCode, modelOptional.get());

        if (carOptional.isEmpty()) {
            Car car = Car.builder()
                    .model(modelOptional.get())
                    .licensePlate(carDTO.licensePlate)
                    .vinCode(carDTO.vinCode)
                    .build();

            carOptional = Optional.of(car);
        }
        return carOptional;
    }

}