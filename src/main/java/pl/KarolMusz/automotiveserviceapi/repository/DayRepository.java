package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.Company;
import pl.KarolMusz.automotiveserviceapi.model.Day;

import java.util.List;
import java.util.UUID;

public interface DayRepository extends JpaRepository<Day, UUID> {
    List<Day> getAllByCompany(Company company);
}
