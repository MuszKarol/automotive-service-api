package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.VisitPatchRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;
import pl.KarolMusz.automotiveserviceapi.service.VisitService;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@RequestMapping("/visits")
@RestController
public class VisitController {

    private final VisitService bookingService;

    @GetMapping("/{id}")
    public ResponseEntity<VisitResponseDTO> getVisitDetails(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok().body(bookingService.getVisitDetailsUsingId(id));
        }
        catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/new")
    public ResponseEntity<List<VisitResponseDTO>> getALlNewVisits() {
        return ResponseEntity.ok().body(bookingService.getAllVisitsWithStatusNew());
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<VisitResponseDTO>> getAllAcceptedVisits() {
        return ResponseEntity.ok().body(bookingService.getAllVisitsWithStatusUnfinished());
    }

    @PostMapping("/new")
    public ResponseEntity<VisitResponseDTO> bookNewVisit(@RequestBody VisitRequestDTO visitRequestDTO) {
        try {
            return ResponseEntity.ok().body(bookingService.createNewVisit(visitRequestDTO));
        } catch (Exception e) {
            return  ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity<VisitResponseDTO> changeVisitStatus(@RequestBody VisitPatchRequestDTO visitPatchRequestDTO) {
        try {
            return ResponseEntity.ok().body(bookingService.updateVisitStatus(visitPatchRequestDTO));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
