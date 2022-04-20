package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.KarolMusz.automotiveserviceapi.model.Model;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ModelRepository extends JpaRepository<Model, UUID> {
    Optional<Model> getModelByBrandNameAndModelName(String brandName, String modelName);
}
