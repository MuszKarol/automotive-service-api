package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Brand extends BasicEntity {

    @Column(nullable = false)
    private String name;
}
