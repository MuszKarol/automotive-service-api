package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.CompanyDetailsDTO;

import javax.persistence.NoResultException;

public interface InformationService {
    CompanyDetailsDTO getCompanyDetails() throws NoResultException;
    CompanyDetailsDTO setCompanyDetails(CompanyDetailsDTO companyDetailsDTO);
}
