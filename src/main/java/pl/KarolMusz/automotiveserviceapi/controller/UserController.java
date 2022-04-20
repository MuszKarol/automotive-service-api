package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.CarDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserCreateRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.UserDTO;
import pl.KarolMusz.automotiveserviceapi.service.UserService;

import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/users")
@RestController
public class UserController {

    private final UserService userService;

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

    @PostMapping("/{id}/vehicle")
    public ResponseEntity<UserDTO> assignCarToUserAccount(@PathVariable(name = "id") UUID userId, @RequestBody CarDTO carDTO) {
        try {
            return ResponseEntity.ok().body(userService.addUserVehicle(userId, carDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{user-id}/vehicle/{vehicle-vin}")
    public ResponseEntity<UserDTO> deleteCarAssignment(@PathVariable("user-id") UUID userId, @PathVariable("vehicle-vin") String vin) {
        try {
            return ResponseEntity.ok().body(userService.deleteUserVehicle(userId, vin));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
