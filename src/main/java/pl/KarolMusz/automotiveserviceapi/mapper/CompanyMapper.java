package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.AddressDTO;
import pl.KarolMusz.automotiveserviceapi.dto.CompanyDetailsDTO;
import pl.KarolMusz.automotiveserviceapi.dto.MechanicalServiceDTO;
import pl.KarolMusz.automotiveserviceapi.dto.DayDTO;
import pl.KarolMusz.automotiveserviceapi.model.Address;
import pl.KarolMusz.automotiveserviceapi.model.Company;
import pl.KarolMusz.automotiveserviceapi.model.MechanicalService;
import pl.KarolMusz.automotiveserviceapi.model.Day;

import java.util.List;

@Mapper
public interface CompanyMapper {

    @Mapping(source = "company.companyName",        target = "companyName")
    @Mapping(source = "company.companyHistory",     target = "description")
    @Mapping(source = "company.phoneNumber",        target = "phoneNumber")
    @Mapping(source = "mechanicalServiceDTOList",   target = "listOfMechanicalServices")
    @Mapping(source = "addressDTO",                 target = "address")
    @Mapping(source = "dayDTOList",                 target = "days")
    CompanyDetailsDTO companyToCompanyDTO(
            Company company,
            List<MechanicalServiceDTO> mechanicalServiceDTOList,
            AddressDTO addressDTO,
            List<DayDTO> dayDTOList
    );

    @Mapping(source = "address.buildingNumber",     target = "buildingNumber")
    @Mapping(source = "address.street",             target = "street")
    @Mapping(source = "address.postalCode",         target = "postalCode")
    @Mapping(source = "address.city",               target = "city")
    @Mapping(source = "address.country",            target = "country")
    AddressDTO addressToCompanyAddressDTO(Address address);

    @Mapping(source = "day.dayName",                target = "dayName")
    @Mapping(source = "day.openingHour",            target = "openingHour")
    @Mapping(source = "day.closingHour",            target = "closingHour")
    DayDTO dayToDayDTO(Day day);

    @Mapping(source = "hoursDTO.dayName",           target = "dayName")
    @Mapping(source = "hoursDTO.openingHour",       target = "openingHour")
    @Mapping(source = "hoursDTO.closingHour",       target = "closingHour")
    @Mapping(source = "company",                    target = "company")
    Day operatingHoursDTOToOperatingHours(DayDTO hoursDTO, Company company);

    @Mapping(source = "serviceDTO.type",            target = "type")
    @Mapping(source = "serviceDTO.cost",            target = "cost")
    @Mapping(source = "serviceDTO.currency",        target = "currency")
    @Mapping(source = "serviceDTO.description",     target = "description")
    @Mapping(source = "company",                    target = "company")
    MechanicalService mechanicalServiceDTOToMechanicalService(MechanicalServiceDTO serviceDTO, Company company);

    @Mapping(source = "service.type",               target = "type")
    @Mapping(source = "service.cost",               target = "cost")
    @Mapping(source = "service.currency",           target = "currency")
    @Mapping(source = "service.description",        target = "description")
    MechanicalServiceDTO mechanicalServiceToMechanicalServiceDTO(MechanicalService service);
}
