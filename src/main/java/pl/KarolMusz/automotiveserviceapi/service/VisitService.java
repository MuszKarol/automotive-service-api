package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.VisitPatchRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;

import java.util.List;
import java.util.UUID;

public interface VisitService {
    VisitResponseDTO getVisitDetailsUsingId(UUID visitId) throws Exception;
    List<VisitResponseDTO> getAllVisitsWithStatusUnfinished();
    List<VisitResponseDTO> getAllVisitsWithStatusNew();
    VisitResponseDTO createNewVisit(VisitRequestDTO visitRequestDTO) throws Exception;
    VisitResponseDTO updateVisitStatus(VisitPatchRequestDTO visitPatchRequestDTO) throws Exception;
}
