package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;

public class CompanyDetailsDTO {
    public String companyName;
    public String description;
    public String phoneNumber;
    public List<MechanicalServiceDTO> listOfMechanicalServices;
    public AddressDTO address;
    public List<DayDTO> days;
}
