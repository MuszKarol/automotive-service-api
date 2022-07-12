package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;
import java.util.UUID;

public class UserDTO {
    public UUID userId;
    public String email;
    public String name;
    public String surname;
    public String role;
    public AddressDTO address;
    public ContactDTO contactDetails;
    public List<CarDTO> carDTOList;
}
