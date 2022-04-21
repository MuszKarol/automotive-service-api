package pl.KarolMusz.automotiveserviceapi.dto;

import java.sql.Date;
import java.util.UUID;

public class VisitResponseDTO {
    public UUID id;
    public String faultDetails;
    public Date carDeliveryDate;
    public Date acceptationDate;
    public Date expectedStartServiceDate;
    public Date expectedEndServiceDate;
    public String clientEmail;
    public String clientPhoneNumber;
    public String serviceStatus;
    public String mechanicEmail;
    public String brandName;
    public String modelName;
    public String vinCode;
}
