package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.*;
import pl.KarolMusz.automotiveserviceapi.mapper.CompanyMapper;
import pl.KarolMusz.automotiveserviceapi.model.Address;
import pl.KarolMusz.automotiveserviceapi.model.Company;
import pl.KarolMusz.automotiveserviceapi.model.MechanicalService;
import pl.KarolMusz.automotiveserviceapi.model.Day;
import pl.KarolMusz.automotiveserviceapi.repository.AddressRepository;
import pl.KarolMusz.automotiveserviceapi.repository.CompanyRepository;
import pl.KarolMusz.automotiveserviceapi.repository.MechanicalServiceRepository;
import pl.KarolMusz.automotiveserviceapi.repository.DayRepository;
import pl.KarolMusz.automotiveserviceapi.service.InformationService;

import javax.persistence.NoResultException;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class InformationServiceImpl implements InformationService {

    private final AddressRepository addressRepository;
    private final MechanicalServiceRepository mechanicalServiceRepository;
    private final DayRepository dayRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyDetailsDTO getCompanyDetails() throws NoResultException {
        Optional<Company> companyOptional = companyRepository.getCompanyByLatestModificationTimestamp();

        if(companyOptional.isEmpty()) {
            throw new NoResultException("Company information not found");
        }

        Company company = companyOptional.get();

        AddressDTO companyAddressDTO = getCompanyAddressDTO(company);
        List<MechanicalServiceDTO> servicesList = getMechanicalServicesDTO(company);
        List<DayDTO> operatingHoursDTOList = getListOfDayDto(company);

        return companyMapper.companyToCompanyDTO(company, servicesList, companyAddressDTO, operatingHoursDTOList);
    }

    @Override
    public CompanyDetailsDTO setCompanyDetails(CompanyDetailsDTO companyDetailsDTO) {
        Address address = findOrCreateNewAddress(companyDetailsDTO.address);

        Company company = createCompany(companyDetailsDTO, address);
        companyRepository.save(company);

        List<Day> dayList = createOperatingHoursList(companyDetailsDTO.days, company);
        dayRepository.saveAll(dayList);

        List<MechanicalService> mechanicalServices = createMechanicalServices(companyDetailsDTO.listOfMechanicalServices, company);
        mechanicalServiceRepository.saveAll(mechanicalServices);

        return companyMapper.companyToCompanyDTO(company, this.getMechanicalServicesDTO(company),
                this.getCompanyAddressDTO(company), companyDetailsDTO.days);
    }

    private Company createCompany(CompanyDetailsDTO requestDTO, Address address) {
        return Company.builder()
                .companyName(requestDTO.companyName)
                .phoneNumber(requestDTO.phoneNumber)
                .companyHistory(requestDTO.description)
                .address(address)
                .modificationTimestamp(new Timestamp(new Date(System.currentTimeMillis()).getTime()))
                .build();
    }

    private List<MechanicalService> createMechanicalServices(List<MechanicalServiceDTO> mechanicalServices, Company company) {
        return mechanicalServiceRepository.saveAll(mechanicalServices.stream()
                .map(service -> companyMapper.mechanicalServiceDTOToMechanicalService(service, company))
                .toList());
    }

    private List<Day> createOperatingHoursList(List<DayDTO> listOfOperatingHours, Company company) {
        return dayRepository.saveAll(listOfOperatingHours.stream()
                .map(operatingHours -> companyMapper.operatingHoursDTOToOperatingHours(operatingHours, company))
                .toList());
    }

    private Address findOrCreateNewAddress(AddressDTO addressDTO) {
        Optional<Address> addressOptional = addressRepository.getAddressByBuildingNumberAndStreetAndCityAndPostalCodeAndCountry(
                addressDTO.buildingNumber, addressDTO.street, addressDTO.city, addressDTO.postalCode, addressDTO.country);

        return addressOptional.orElseGet(() -> addressRepository.save(
                Address.builder()
                .buildingNumber(addressDTO.buildingNumber)
                .street(addressDTO.street)
                .city(addressDTO.city)
                .postalCode(addressDTO.postalCode)
                .country(addressDTO.country)
                .build()));
    }

    private AddressDTO getCompanyAddressDTO(Company company) {
        return companyMapper.addressToCompanyAddressDTO(company.getAddress());
    }

    private List<MechanicalServiceDTO> getMechanicalServicesDTO(Company company) {
        return mechanicalServiceRepository.getAllByCompany(company).stream()
                .map(companyMapper::mechanicalServiceToMechanicalServiceDTO)
                .toList();
    }

    private List<DayDTO> getListOfDayDto(Company company) {
        return dayRepository.getAllByCompany(company).stream()
                .map(companyMapper::dayToDayDTO)
                .toList();
    }
}
