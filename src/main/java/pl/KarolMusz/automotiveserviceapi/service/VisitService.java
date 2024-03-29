package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.VisitPatchRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface VisitService {
    List<VisitResponseDTO> getAllUserVisits();
    List<VisitResponseDTO> getAllVisitsWithStatusUnfinished();
    List<VisitResponseDTO> getAllVisitsWithStatusNew();
    VisitResponseDTO createNewVisit(VisitRequestDTO visitRequestDTO) throws EntityNotFoundException;
    VisitResponseDTO updateVisitStatus(VisitPatchRequestDTO visitPatchRequestDTO) throws EntityNotFoundException;
}
