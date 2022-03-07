package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;
import java.util.UUID;

public class CompanyInformationsResponseDTO {
    public UUID id;
    public String automotiveServiceName;
    public String description;
    public String phoneNumber;
    public List<MechanicalServiceDTO> listOfMechanicalServices;
    public CompanyAddressDTO address;
    public List<OperatingHoursDTO> listOfOperatingHours;
}
