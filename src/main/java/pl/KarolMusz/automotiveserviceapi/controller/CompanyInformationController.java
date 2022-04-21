package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.CompanyDetailsDTO;
import pl.KarolMusz.automotiveserviceapi.service.InformationService;

@AllArgsConstructor
@RestController
@RequestMapping("/about")
public class CompanyInformationController {

    private final InformationService informationService;

    @GetMapping
    public ResponseEntity<CompanyDetailsDTO> getAutomotiveServiceDetails() {
        try {
            return ResponseEntity.ok().body(informationService.getCompanyDetails());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<CompanyDetailsDTO> setAutomotiveServiceDetails(@RequestBody CompanyDetailsDTO companyDetailsDTO) {
        return ResponseEntity.ok().body(informationService.setCompanyDetails(companyDetailsDTO));
    }
}
