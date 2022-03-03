package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;

public class AutomotiveServiceDataRequestDTO {
    public String automotiveServiceName;
    public String description;
    public String phoneNumber;
    public List<String> listOfServices;
    public AutomotiveServiceAddressDTO address;
    public List<OperatingHoursDTO> listOfOperatingHours;
}
