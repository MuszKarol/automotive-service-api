package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.CompanyAddressDTO;
import pl.KarolMusz.automotiveserviceapi.dto.CompanyInformationsResponseDTO;
import pl.KarolMusz.automotiveserviceapi.dto.MechanicalServiceDTO;
import pl.KarolMusz.automotiveserviceapi.dto.OperatingHoursDTO;
import pl.KarolMusz.automotiveserviceapi.model.Address;
import pl.KarolMusz.automotiveserviceapi.model.Company;
import pl.KarolMusz.automotiveserviceapi.model.MechanicalService;
import pl.KarolMusz.automotiveserviceapi.model.OperatingHours;

import java.util.List;

@Mapper
public interface CompanyMapper {

    @Mapping(source = "company.id",                 target = "id")
    @Mapping(source = "company.companyName",        target = "automotiveServiceName")
    @Mapping(source = "company.companyHistory",     target = "description")
    @Mapping(source = "company.phoneNumber",        target = "phoneNumber")
    @Mapping(source = "mechanicalServiceDTOList",   target = "listOfMechanicalServices")
    @Mapping(source = "addressDTO",                 target = "address")
    @Mapping(source = "operatingHoursDTOList",      target = "listOfOperatingHours")
    CompanyInformationsResponseDTO companyToCompanyDTO(Company company,
                                                       List<MechanicalServiceDTO> mechanicalServiceDTOList,
                                                       CompanyAddressDTO addressDTO,
                                                       List<OperatingHoursDTO> operatingHoursDTOList);


    @Mapping(source = "address.buildingNumber",     target = "buildingNumber")
    @Mapping(source = "address.street",             target = "street")
    @Mapping(source = "address.postalCode",         target = "postalCode")
    @Mapping(source = "address.city",               target = "city")
    @Mapping(source = "address.country",            target = "country")
    CompanyAddressDTO addressToCompanyAddressDTO(Address address);


    @Mapping(source = "hours.dayName",              target = "dayName")
    @Mapping(source = "hours.openingHour",          target = "openingHour")
    @Mapping(source = "hours.closingHour",          target = "closingHour")
    OperatingHoursDTO operatingHoursToOperatingHoursDTO(OperatingHours hours);


    @Mapping(source = "hoursDTO.dayName",           target = "dayName")
    @Mapping(source = "hoursDTO.openingHour",       target = "openingHour")
    @Mapping(source = "hoursDTO.closingHour",       target = "closingHour")
    OperatingHours operatingHoursDTOToOperatingHours(OperatingHoursDTO hoursDTO);


    @Mapping(source = "serviceDTO.type",            target = "type")
    @Mapping(source = "serviceDTO.cost",            target = "cost")
    @Mapping(source = "serviceDTO.description",     target = "description")
    MechanicalService mechanicalServiceDTOToMechanicalService(MechanicalServiceDTO serviceDTO);


    @Mapping(source = "service.type",               target = "type")
    @Mapping(source = "service.cost",               target = "cost")
    @Mapping(source = "service.description",        target = "description")
    MechanicalServiceDTO mechanicalServiceToMechanicalServiceDTO(MechanicalService service);
}
