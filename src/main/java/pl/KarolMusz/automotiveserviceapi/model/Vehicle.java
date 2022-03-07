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
@Entity(name = "vehicle")
public class Vehicle extends BasicEntity {

    @Column(nullable = false)
    private String vehicleType = "car";

    private String version;

    @Column(nullable = false)
    private String engineType;

    @Column(nullable = false)
    private String engineCapacity;

    private String enginePower;

    @ManyToOne
    private Model model;
}
