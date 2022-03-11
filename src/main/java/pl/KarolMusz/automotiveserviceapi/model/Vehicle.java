package pl.KarolMusz.automotiveserviceapi.model;


import lombok.*;

import javax.persistence.*;

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
    private String engineType;
    private String engineCapacity;
    private String enginePower;

    @ManyToOne
    private Model model;
}
