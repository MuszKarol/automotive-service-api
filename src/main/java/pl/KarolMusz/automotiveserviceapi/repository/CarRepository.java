package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.KarolMusz.automotiveserviceapi.model.Car;
import pl.KarolMusz.automotiveserviceapi.model.Model;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CarRepository extends JpaRepository<Car, UUID> {
//    @Query(value = "SELECT c FROM car c WHERE c.model.name = :carModel AND c.VIN = :vinCode")
//    Optional<Car> getCarByModelAndVinCode(@Param("carModel") String model, @Param("vinCode") String vinCode);
    Optional<Car> getCarByVinCode(String vinCode);
    Optional<Car> getCarByVinCodeAndModel(String vinCode, Model model);
}
