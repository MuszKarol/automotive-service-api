package pl.KarolMusz.automotiveserviceapi.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "number_of_parts")
public class Quantity extends BasicEntity{

    private Integer number;

    @ManyToOne
    private Part part;
}
