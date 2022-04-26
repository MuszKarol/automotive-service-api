package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.User;
import pl.KarolMusz.automotiveserviceapi.model.Visit;
import pl.KarolMusz.automotiveserviceapi.model.enums.ServiceStatus;

import java.util.List;
import java.util.UUID;

public interface VisitRepository extends JpaRepository<Visit, UUID> {
    List<Visit> getAllByServiceStatus(ServiceStatus serviceStatus);
    List<Visit> getAllByClient(User client);
}
