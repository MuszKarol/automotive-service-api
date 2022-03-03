package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_table")
public class User extends BasicEntity {

    @Column(nullable = false)
    private String email;

    private String name;
    private String surname;

    @OneToMany
    private List<Vehicle> carList;
}
