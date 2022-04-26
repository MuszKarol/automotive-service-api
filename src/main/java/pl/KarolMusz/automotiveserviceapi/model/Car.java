package pl.KarolMusz.automotiveserviceapi.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

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

    @ManyToOne
    private Model model;
}
