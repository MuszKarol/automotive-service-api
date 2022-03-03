package pl.KarolMusz.automotiveserviceapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.KarolMusz.automotiveserviceapi.dto.AutomotiveServiceDataRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.AutomotiveServiceDataResponseDTO;

@RestController
@RequestMapping("/about")
public class InformationsController {

    @GetMapping
    public ResponseEntity<AutomotiveServiceDataResponseDTO> getAutomotiveServiceInformations() {
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/new")
    public ResponseEntity<AutomotiveServiceDataResponseDTO> setAutomotiveServiceInformations(
            AutomotiveServiceDataRequestDTO automotiveServiceDataRequestDTO)
    {
        return ResponseEntity.notFound().build();
    }
}
