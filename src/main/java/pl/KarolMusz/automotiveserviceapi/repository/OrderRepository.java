package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.KarolMusz.automotiveserviceapi.model.Order;
import pl.KarolMusz.automotiveserviceapi.model.enums.OrderStatus;

import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    List<Order> findAllByStatus(OrderStatus status);
}
