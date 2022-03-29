package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="part")
public class Part extends BasicEntity {

    @Column(unique = true)
    private String code;

    private String name;
    private String price;
}
