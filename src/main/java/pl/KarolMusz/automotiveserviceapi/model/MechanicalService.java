package pl.KarolMusz.automotiveserviceapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "service")
public class MechanicalService extends BasicEntity {

    private String type;
    private float cost;
    public String currency;
    private String description;

    @ManyToOne
    private Company company;
}
