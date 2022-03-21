package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;

public class OrderCreateRequestDTO {
    public String status;
    public String userEmail;
    public List<PartDTO> parts;
}
