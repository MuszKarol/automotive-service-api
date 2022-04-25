package pl.KarolMusz.automotiveserviceapi.dto;

import java.sql.Date;

public class VisitRequestDTO {
    public String faultDetails;
    public Date carDeliveryDate;
    public Date acceptationDate;
    public Date expectedStartServiceDate;
    public Date expectedEndServiceDate;
    public String vinCode;
}
