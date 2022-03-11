package pl.KarolMusz.automotiveserviceapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactDetails extends BasicEntity {

    @Column(nullable = false)
    private String phoneNumber;

    private String secondEmail;
}
