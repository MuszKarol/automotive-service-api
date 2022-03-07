package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import pl.KarolMusz.automotiveserviceapi.model.Company;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    @Query("SELECT c FROM company_details c WHERE c.modificationTimestamp = " +
            "(SELECT MAX(sc.modificationTimestamp) FROM company_details sc)")
    Optional<Company> getCompanyByLatestModificationTimestamp();
}
