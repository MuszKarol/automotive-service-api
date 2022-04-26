package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;
import pl.KarolMusz.automotiveserviceapi.model.Visit;

@Mapper
public interface VisitMapper {

    @Mapping(source = "visit.id",                                   target = "id")
    @Mapping(source = "visit.faultDetails",                         target = "faultDetails")
    @Mapping(source = "visit.carDeliveryDate",                      target = "carDeliveryDate")
    @Mapping(source = "visit.serviceStatus",                        target = "serviceStatus")
    @Mapping(source = "visit.acceptationDate",                      target = "acceptationDate")
    @Mapping(source = "visit.expectedStartServiceDate",             target = "expectedStartServiceDate")
    @Mapping(source = "visit.expectedEndServiceDate",               target = "expectedEndServiceDate")
    @Mapping(source = "visit.client.email",                         target = "clientEmail")
    @Mapping(source = "visit.client.contactDetails.phoneNumber",    target = "clientPhoneNumber")
    @Mapping(source = "visit.car.model.brandName",                  target = "brandName")
    @Mapping(source = "visit.car.model.modelName",                  target = "modelName")
    @Mapping(source = "visit.car.vinCode",                          target = "vinCode")
    VisitResponseDTO visitToVisitResponseDTO(Visit visit);
}
