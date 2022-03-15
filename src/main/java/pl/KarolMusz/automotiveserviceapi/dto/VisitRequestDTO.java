package pl.KarolMusz.automotiveserviceapi.dto;

import java.sql.Date;

public class VisitRequestDTO {
    public String faultDetails;
    public Date bookingDate;
    public Date acceptationDate;
    public Date expectedStartServiceDate;
    public Date expectedEndServiceDate;
    public String clientEmail;
    public String mechanicEmail;
    public String carModel;
    public String carVersion;
}
