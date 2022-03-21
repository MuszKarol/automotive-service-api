package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ContactDetails extends BasicEntity {

    @Column(nullable = false)
    private String phoneNumber;

    private String secondEmail;

    @Override
    public String toString() {
        return  phoneNumber + ' ' + secondEmail;
    }
}
