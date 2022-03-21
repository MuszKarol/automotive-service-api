package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;

public class UserCreateRequestDTO {
    public String email;
    public String name;
    public String surname;
    public String role;
    public AddressDTO address;
    public ContactDTO contactDetails;
    public List<CarDTO> carDTOList;
}
