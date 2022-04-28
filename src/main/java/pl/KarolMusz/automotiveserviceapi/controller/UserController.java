package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.CarDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserCreateRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserDTO;
import pl.KarolMusz.automotiveserviceapi.service.UserService;

import javax.persistence.EntityNotFoundException;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser() {
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        try {
            return ResponseEntity.ok().body(userService.getUser());
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") UUID uuid) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(uuid));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<UserDTO> createNewUser(@RequestBody UserCreateRequestDTO userCreateRequestDTO) {
        try {
            return ResponseEntity.ok().body(userService.createUser(userCreateRequestDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<UserDTO> updateUserDetails(@RequestBody UserDTO userDTO) {
        try {
            return ResponseEntity.ok().body(userService.updateUserDetails(userDTO));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/vehicle")
    public ResponseEntity<UserDTO> assignCarToUserAccount(@RequestBody CarDTO carDTO) {
        try {
            return ResponseEntity.ok().body(userService.addUserVehicle(carDTO));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/vehicle/{vehicle-vin}")
    public ResponseEntity<UserDTO> deleteCarAssignment(@PathVariable("vehicle-vin") String vin) {
        try {
            return ResponseEntity.ok().body(userService.deleteUserVehicle(vin));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
