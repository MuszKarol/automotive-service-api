package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.*;
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

    @JoinColumn(nullable = true)
    @OneToOne
    private Address address;

    @ManyToMany
    private List<Vehicle> listOfVehicles;

    @OneToOne
    private ContactDetails contactDetails;
}
