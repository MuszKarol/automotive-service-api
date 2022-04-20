package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Model extends BasicEntity {

    @Column(nullable = false)
    private String modelName;

    @Column(nullable = false)
    private String brandName;

    private String versions;
    private String engines;
}
