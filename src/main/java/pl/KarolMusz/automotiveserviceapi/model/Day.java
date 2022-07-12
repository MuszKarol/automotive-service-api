package pl.KarolMusz.automotiveserviceapi.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "opening_hours")
public class Day extends BasicEntity {
    private String dayName;
    private String openingHour;
    private String closingHour;

    @ManyToOne
    private Company company;
}
