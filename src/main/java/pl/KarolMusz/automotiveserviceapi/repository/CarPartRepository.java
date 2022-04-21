package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.CarPart;

import java.util.Optional;
import java.util.UUID;

public interface CarPartRepository extends JpaRepository<CarPart, UUID> {
    Optional<CarPart> getCarPartByCode(String code);
}
