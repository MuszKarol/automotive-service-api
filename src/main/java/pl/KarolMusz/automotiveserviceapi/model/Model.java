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
@Entity
public class Model extends BasicEntity {

    private String name;

    @ManyToOne
    private Brand brand;
}
