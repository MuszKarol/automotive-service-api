package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.*;
import pl.KarolMusz.automotiveserviceapi.mapper.CompanyMapper;
import pl.KarolMusz.automotiveserviceapi.model.Address;
import pl.KarolMusz.automotiveserviceapi.model.Company;
import pl.KarolMusz.automotiveserviceapi.model.MechanicalService;
import pl.KarolMusz.automotiveserviceapi.model.OperatingHours;
import pl.KarolMusz.automotiveserviceapi.repository.AddressRepository;
import pl.KarolMusz.automotiveserviceapi.repository.CompanyRepository;
import pl.KarolMusz.automotiveserviceapi.repository.MechanicalServiceRepository;
import pl.KarolMusz.automotiveserviceapi.repository.OperatingHoursRepository;
import pl.KarolMusz.automotiveserviceapi.service.InformationService;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;


@AllArgsConstructor
@Service
public class InformationServiceImpl implements InformationService {

    private final AddressRepository addressRepository;
    private final MechanicalServiceRepository mechanicalServiceRepository;
    private final OperatingHoursRepository operatingHoursRepository;
    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    @Override
    public CompanyInformationsResponseDTO getCompanyInformations() throws Exception {
        Optional<Company> companyOptional = companyRepository.getCompanyByLatestModificationTimestamp();

        if(companyOptional.isEmpty()) throw new Exception(); //TODO

        Company company = companyOptional.get();

        CompanyAddressDTO companyAddressDTO = getCompanyAddressDTO(company);
        List<MechanicalServiceDTO> servicesList = getMechanicalServicesDTO(company);
        List<OperatingHoursDTO> operatingHoursDTOList = getOperatingHoursDTOS(company);

        return companyMapper.companyToCompanyDTO(company, servicesList, companyAddressDTO, operatingHoursDTOList);
    }

    @Override
    public CompanyInformationsResponseDTO setCompanyInformations(CompanyInformationsRequestDTO requestDTO) {
        Address address = findOrCreateNewAddress(requestDTO.address);
        List<OperatingHours> operatingHoursList = createOperatingHoursList(requestDTO.listOfOperatingHours);
        List<MechanicalService> mechanicalServices = createMechanicalServices(requestDTO.listOfMechanicalServices);
        Company company = createCompany(requestDTO, address, operatingHoursList, mechanicalServices);

        companyRepository.save(company);

        return companyMapper.companyToCompanyDTO(company, requestDTO.listOfMechanicalServices, requestDTO.address,
                requestDTO.listOfOperatingHours);
    }

    private Company createCompany(CompanyInformationsRequestDTO requestDTO, Address address,
                                  List<OperatingHours> operatingHoursList, List<MechanicalService> mechanicalServices)
    {
        return Company.builder()
                .companyName(requestDTO.companyName)
                .phoneNumber(requestDTO.phoneNumber)
                .companyHistory(requestDTO.description)
                .address(address)
                .operatingHoursPerWeek(operatingHoursList)
                .mechanicalServices(mechanicalServices)
                .modificationTimestamp(new Timestamp(new Date(System.currentTimeMillis()).getTime()))
                .build();
    }

    private List<MechanicalService> createMechanicalServices(List<MechanicalServiceDTO> mechanicalServices) {
        return mechanicalServiceRepository.saveAll(mechanicalServices.stream()
                .map(companyMapper::mechanicalServiceDTOToMechanicalService)
                .toList());
    }

    private List<OperatingHours> createOperatingHoursList(List<OperatingHoursDTO> listOfOperatingHours) {
        return operatingHoursRepository.saveAll(listOfOperatingHours.stream()
                .map(companyMapper::operatingHoursDTOToOperatingHours)
                .toList());
    }

    private Address findOrCreateNewAddress(CompanyAddressDTO addressDTO) {
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

    private CompanyAddressDTO getCompanyAddressDTO(Company company) {
        return companyMapper.addressToCompanyAddressDTO(company.getAddress());
    }

    private List<MechanicalServiceDTO> getMechanicalServicesDTO(Company company) {
        return company.getMechanicalServices().stream()
                .map(companyMapper::mechanicalServiceToMechanicalServiceDTO)
                .toList();
    }

    private List<OperatingHoursDTO> getOperatingHoursDTOS(Company company) {
        return company.getOperatingHoursPerWeek().stream()
                .map(companyMapper::operatingHoursToOperatingHoursDTO)
                .toList();
    }
}
