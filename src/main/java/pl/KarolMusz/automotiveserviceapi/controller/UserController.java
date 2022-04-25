package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.*;
import pl.KarolMusz.automotiveserviceapi.service.UserService;

import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateUser() {
        return  ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<UserDTO> getUser() {
        return ResponseEntity.ok().body(userService.getUser());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name = "id") UUID uuid) {
        try {
            return ResponseEntity.ok().body(userService.getUserById(uuid));
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/vehicle")
    public ResponseEntity<UserDTO> assignCarToUserAccount(@RequestBody CarDTO carDTO) {
        try {
            return ResponseEntity.ok().body(userService.addUserVehicle(carDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/vehicle/{vehicle-vin}")
    public ResponseEntity<UserDTO> deleteCarAssignment(@PathVariable("vehicle-vin") String vin) {
        try {
            return ResponseEntity.ok().body(userService.deleteUserVehicle(vin));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
