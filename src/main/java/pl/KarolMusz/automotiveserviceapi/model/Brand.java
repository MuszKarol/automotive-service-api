package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Brand extends BasicEntity {
    private String name;
}
