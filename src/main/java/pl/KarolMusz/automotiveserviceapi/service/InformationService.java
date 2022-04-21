package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.CompanyDetailsDTO;

public interface InformationService {
    CompanyDetailsDTO getCompanyDetails() throws Exception;
    CompanyDetailsDTO setCompanyDetails(CompanyDetailsDTO companyDetailsDTO);
}
