package pl.KarolMusz.automotiveserviceapi.dto;

import java.sql.Date;
import java.util.UUID;

public class VisitPatchRequestDTO {
    public UUID visitId;
    public String visitStatus;
    public Date expectedStartServiceDate;
    public Date expectedEndServiceDate;
    public String mechanicEmail;
}
