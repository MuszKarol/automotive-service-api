package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.Company;
import pl.KarolMusz.automotiveserviceapi.model.MechanicalService;

import java.util.List;
import java.util.UUID;

public interface MechanicalServiceRepository extends JpaRepository<MechanicalService, UUID> {
    List<MechanicalService> getAllByCompany(Company company);
}
