package pl.KarolMusz.automotiveserviceapi.dto;

import java.sql.Date;
import java.util.UUID;

public class VisitPatchRequestDTO {
    public UUID id;
    public String serviceStatus;
    public Date carDeliveryDate;
    public Date expectedStartServiceDate;
    public Date expectedEndServiceDate;
}
