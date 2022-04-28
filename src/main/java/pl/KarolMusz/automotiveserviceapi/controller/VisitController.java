package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.VisitPatchRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;
import pl.KarolMusz.automotiveserviceapi.service.VisitService;

import javax.persistence.EntityNotFoundException;
import java.util.List;


@AllArgsConstructor
@RequestMapping("/visits")
@RestController
public class VisitController {

    private final VisitService visitService;

    @GetMapping("/client")
    public ResponseEntity<List<VisitResponseDTO>> getAllClientVisits() {
        return ResponseEntity.ok().body(visitService.getAllUserVisits());
    }

    @GetMapping("/new")
    public ResponseEntity<List<VisitResponseDTO>> getALlNewVisits() {
        return ResponseEntity.ok().body(visitService.getAllVisitsWithStatusNew());
    }

    @GetMapping("/accepted")
    public ResponseEntity<List<VisitResponseDTO>> getAllAcceptedVisits() {
        return ResponseEntity.ok().body(visitService.getAllVisitsWithStatusUnfinished());
    }

    @PostMapping("/new")
    public ResponseEntity<VisitResponseDTO> saveNewVisit(@RequestBody VisitRequestDTO visitRequestDTO) {
        try {
            return ResponseEntity.ok().body(visitService.createNewVisit(visitRequestDTO));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PatchMapping
    public ResponseEntity<VisitResponseDTO> changeVisitStatus(@RequestBody VisitPatchRequestDTO visitPatchRequestDTO) {
        try {
            return ResponseEntity.ok().body(visitService.updateVisitStatus(visitPatchRequestDTO));
        } catch (EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

}
