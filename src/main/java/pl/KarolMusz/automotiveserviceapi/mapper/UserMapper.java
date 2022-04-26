package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.*;
import pl.KarolMusz.automotiveserviceapi.model.Address;
import pl.KarolMusz.automotiveserviceapi.model.ContactDetails;
import pl.KarolMusz.automotiveserviceapi.model.User;

import java.util.List;

@Mapper
public interface UserMapper {

    @Mapping(source = "address.buildingNumber", target = "buildingNumber")
    @Mapping(source = "address.street",         target = "street")
    @Mapping(source = "address.postalCode",     target = "postalCode")
    @Mapping(source = "address.city",           target = "city")
    @Mapping(source = "address.country",        target = "country")
    AddressDTO addressToAddressDTO(Address address);

    @Mapping(source = "contact.phoneNumber",    target = "phoneNumber")
    @Mapping(source = "contact.secondEmail",    target = "secondEmail")
    ContactDTO contactToContactDTO(ContactDetails contact);

    @Mapping(source = "user.id",                target = "userId")
    @Mapping(source = "user.email",             target = "email")
    @Mapping(source = "user.name",              target = "name")
    @Mapping(source = "user.surname",           target = "surname")
    @Mapping(source = "user.role",              target = "role")
    @Mapping(source = "addressDTO",             target = "address")
    @Mapping(source = "contactDTO",             target = "contactDetails")
    @Mapping(source = "carDTOs",                target = "carDTOList")
    UserDTO userToUserDTO(User user, AddressDTO addressDTO, ContactDTO contactDTO, List<CarDTO> carDTOs);

    @Mapping(source = "user.id",                target = "userId")
    @Mapping(source = "user.email",             target = "email")
    @Mapping(source = "user.role",              target = "role")
    @Mapping(source = "token",                  target = "token")
    AuthenticationResponseDTO userToAuthenticationRequestDTO(User user, String token);
}
