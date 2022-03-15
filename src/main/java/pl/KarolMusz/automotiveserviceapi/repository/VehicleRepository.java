package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.KarolMusz.automotiveserviceapi.model.Vehicle;

import java.util.Optional;
import java.util.UUID;

public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
    @Query(value = "SELECT v FROM vehicle v WHERE v.vehicleType LIKE 'car' AND v.model.name = :carModel " +
            "AND v.version = :carVersion")
    Optional<Vehicle> getVehicleByModelAndVersion(@Param("carModel") String carModel,
                                                  @Param("carVersion") String carVersion);
}
