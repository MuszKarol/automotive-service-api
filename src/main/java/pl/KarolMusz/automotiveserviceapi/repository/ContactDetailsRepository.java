package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.ContactDetails;

import java.util.Optional;
import java.util.UUID;

public interface ContactDetailsRepository extends JpaRepository<ContactDetails, UUID> {
    Optional<ContactDetails> getContactDetailsByPhoneNumberAndSecondEmail(String phoneNumber, String secondEmail);
}
