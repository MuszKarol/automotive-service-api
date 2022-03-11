package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Part extends BasicEntity {
    private String code;
    private String name;
    private String price;
}
