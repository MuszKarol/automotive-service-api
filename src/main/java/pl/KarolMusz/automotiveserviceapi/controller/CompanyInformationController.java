package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.CompanyInformationsRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.CompanyInformationsResponseDTO;
import pl.KarolMusz.automotiveserviceapi.service.InformationService;

@AllArgsConstructor
@RestController
@RequestMapping("/about")
public class CompanyInformationController {

    private final InformationService informationsService;

    @GetMapping
    public ResponseEntity<CompanyInformationsResponseDTO> getAutomotiveServiceInformations() {
        try {
            return ResponseEntity.ok().body(informationsService.getCompanyInformations());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<CompanyInformationsResponseDTO> setAutomotiveServiceInformations(
            @RequestBody CompanyInformationsRequestDTO companyInformationsRequestDTO) {
        return ResponseEntity.ok().body(informationsService.setCompanyInformations(companyInformationsRequestDTO));
    }
}
