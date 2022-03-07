package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;

public class CompanyInformationsRequestDTO {
    public String companyName;
    public String description;
    public String phoneNumber;
    public List<MechanicalServiceDTO> listOfMechanicalServices;
    public CompanyAddressDTO address;
    public List<OperatingHoursDTO> listOfOperatingHours;
}
