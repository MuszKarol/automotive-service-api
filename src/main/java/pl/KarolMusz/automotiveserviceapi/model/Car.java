package pl.KarolMusz.automotiveserviceapi.model;


import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "car")
public class Car extends BasicEntity {

    @Column(unique = true, name = "VIN")
    private String vinCode;

    @Column(unique = true)
    private String licensePlate;

    @ManyToOne
    private Model model;
}
