package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.CompanyInformationsRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.CompanyInformationsResponseDTO;

public interface InformationService {
    CompanyInformationsResponseDTO getCompanyInformations() throws Exception;
    CompanyInformationsResponseDTO setCompanyInformations(CompanyInformationsRequestDTO companyInformationsRequestDTO);
}
