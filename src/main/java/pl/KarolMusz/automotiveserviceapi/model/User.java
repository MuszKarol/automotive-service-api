package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;
import pl.KarolMusz.automotiveserviceapi.model.enums.Role;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_table")
public class User extends BasicEntity {

    @Column(nullable = false, unique = true)
    private String email;

    private String name;
    private String surname;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    @JoinColumn(nullable = true)
    @OneToOne
    private Address address;

    @ManyToMany
    private List<Car> listOfCars;

    @OneToOne
    private ContactDetails contactDetails;
}
