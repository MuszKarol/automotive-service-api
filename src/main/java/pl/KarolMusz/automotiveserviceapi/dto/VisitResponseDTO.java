package pl.KarolMusz.automotiveserviceapi.dto;

import java.sql.Date;
import java.util.UUID;

public class VisitResponseDTO {
    public UUID id;
    public String faultDetails;
    public Date bookingDate;
    public Date acceptationDate;
    public Date expectedStartServiceDate;
    public Date expectedEndServiceDate;
    public String clientEmail;
    public String serviceStatus;
    public String mechanicEmail;
    public String carModel;
}
