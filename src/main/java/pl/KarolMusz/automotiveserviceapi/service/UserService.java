package pl.KarolMusz.automotiveserviceapi.service;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import pl.KarolMusz.automotiveserviceapi.dto.CarDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserCreateRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserDTO;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.UUID;

public interface UserService {
    UserDTO getUser() throws UsernameNotFoundException;
    UserDTO getUserById(UUID uuid) throws EntityNotFoundException;
    UserDTO createUser(UserCreateRequestDTO userCreateRequestDTO) throws EntityExistsException;
    UserDTO updateUserDetails(UserDTO userDTO) throws UsernameNotFoundException;
    UserDTO addUserVehicle(CarDTO carDTO) throws UsernameNotFoundException, EntityExistsException, EntityNotFoundException;
    UserDTO deleteUserVehicle(String vin) throws EntityNotFoundException;
}
