package pl.KarolMusz.automotiveserviceapi.dto;

import java.util.List;
import java.util.UUID;

public class OrderDTO {
    public UUID orderId;
    public String status;
    public String userEmail;
    public List<PartDTO> parts;
}
