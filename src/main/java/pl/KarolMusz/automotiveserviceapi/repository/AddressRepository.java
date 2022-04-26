package pl.KarolMusz.automotiveserviceapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.KarolMusz.automotiveserviceapi.model.Address;

import java.util.Optional;
import java.util.UUID;

public interface AddressRepository extends JpaRepository<Address, UUID> {
    Optional<Address> getAddressByBuildingNumberAndStreetAndCityAndPostalCodeAndCountry(
            String buildingNumber,
            String street,
            String city,
            String postalCode,
            String country
    );
}
