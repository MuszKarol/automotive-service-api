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
    UserDTO addUserVehicle(UUID userId, CarDTO carDTO) throws Exception;
    UserDTO deleteUserVehicle(UUID userId, UUID carId) throws Exception;
}
