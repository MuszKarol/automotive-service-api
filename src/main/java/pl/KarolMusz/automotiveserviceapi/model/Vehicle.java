package pl.KarolMusz.automotiveserviceapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "car")
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
