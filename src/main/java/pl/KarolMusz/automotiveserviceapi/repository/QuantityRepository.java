package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.Quantity;

import java.util.UUID;

public interface QuantityRepository extends JpaRepository<Quantity, UUID> {
}
