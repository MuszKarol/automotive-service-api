package pl.KarolMusz.automotiveserviceapi.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "car")
public class Car extends BasicEntity {

    @Column(unique = true, name = "VIN")
    private String vinCode;

    private String licensePlate;
    private Date carRegistrationDate;

    private String version;
    private String engine;

    @ManyToOne
    private Model model;
}
