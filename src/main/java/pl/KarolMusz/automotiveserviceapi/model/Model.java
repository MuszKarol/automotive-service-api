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
@Entity
public class Model extends BasicEntity {

    @Column(nullable = false)
    private String name;

    private String versions;
    private String engines;

    @ManyToOne
    private Brand brand;
}
