package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "company_details")
public class Company extends BasicEntity {

    @Column(nullable = false)
    private Timestamp modificationTimestamp;

    @Column(nullable = false)
    private String companyName;

    @Column(length = 1024)
    private String companyHistory;

    private String phoneNumber;

    @OneToMany
    private List<OperatingHours> operatingHoursPerWeek;

    @OneToOne
    private Address address;

    @OneToMany
    private List<MechanicalService> mechanicalServices;
}
