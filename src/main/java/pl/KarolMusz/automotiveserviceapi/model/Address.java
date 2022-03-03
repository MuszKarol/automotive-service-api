package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.Entity;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Address extends BasicEntity {
    private String buildingNumber;
    private String street;
    private String postalCode;
    private String city;
    private String country;
}
