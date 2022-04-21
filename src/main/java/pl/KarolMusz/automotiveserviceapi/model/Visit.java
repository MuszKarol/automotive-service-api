package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;
import pl.KarolMusz.automotiveserviceapi.model.enums.ServiceStatus;

import javax.persistence.*;
import java.sql.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Visit extends BasicEntity{

    private String faultDetails;

    @Enumerated(EnumType.STRING)
    private ServiceStatus serviceStatus;

    private Date carDeliveryDate;
    private Date acceptationDate;
    private Date expectedStartServiceDate;
    private Date expectedEndServiceDate;

    @OneToOne
    private User client;

    @JoinColumn(nullable = true)
    @OneToOne
    private User mechanic;

    @OneToOne
    private Car car;
}
