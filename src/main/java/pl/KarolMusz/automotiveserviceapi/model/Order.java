package pl.KarolMusz.automotiveserviceapi.model;

import lombok.*;
import pl.KarolMusz.automotiveserviceapi.model.enums.OrderStatus;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_table")
public class Order extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne
    private User creator;

//    @OneToMany
//    private List<Part> parts;

    @OneToMany
    private List<Quantity> numberOfPartsList;

//    @ElementCollection
//    @CollectionTable(name = "order_items", joinColumns = @JoinColumn(name = "order_id"))
//    @Column(name = "amount")
//    @MapKeyJoinColumn(name = "part_FK")
//    Map<Part, Integer> listOfPart;
}
