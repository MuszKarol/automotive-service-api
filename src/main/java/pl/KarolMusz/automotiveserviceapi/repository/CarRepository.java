package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.Car;

import java.util.UUID;

public interface CarRepository extends JpaRepository<Car, UUID> {

}
