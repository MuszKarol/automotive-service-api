package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.OperatingHours;

import java.util.UUID;

public interface OperatingHoursRepository extends JpaRepository<OperatingHours, UUID> {
}
