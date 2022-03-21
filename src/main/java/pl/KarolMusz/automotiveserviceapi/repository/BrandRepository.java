package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.Brand;

import java.util.Optional;
import java.util.UUID;

public interface BrandRepository extends JpaRepository<Brand, UUID> {
    Optional<Brand> getBrandByName(String name);
}
