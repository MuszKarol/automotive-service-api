package pl.KarolMusz.automotiveserviceapi.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.KarolMusz.automotiveserviceapi.model.enums.OrderStatus;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "order_table")
public class Order extends BasicEntity {

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @OneToOne
    private User creator;

    @OneToMany
    private List<Part> parts;
}
