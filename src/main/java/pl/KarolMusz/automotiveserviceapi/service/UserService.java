package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.CarDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserCreateRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserDTO;

import java.util.UUID;

public interface UserService {
    UserDTO getUser();
    UserDTO getUserById(UUID uuid) throws Exception;
    UserDTO createUser(UserCreateRequestDTO userCreateRequestDTO) throws Exception;
    UserDTO updateUserDetails(UserDTO userDTO) throws Exception;
    UserDTO addUserVehicle(CarDTO carDTO) throws Exception;
    UserDTO deleteUserVehicle(String vin) throws Exception;
}
