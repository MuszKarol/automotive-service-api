package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;
import java.util.UUID;

public class AutomotiveServiceDataResponseDTO {
    public UUID id;
    public String automotiveServiceName;
    public String description;
    public String phoneNumber;
    public List<String> listOfServices;
    public AutomotiveServiceAddressDTO address;
    public List<OperatingHoursDTO> listOfOperatingHours;
}
