package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;
import pl.KarolMusz.automotiveserviceapi.model.Visit;

@Mapper
public interface VisitMapper {

    @Mapping(source = "visit.id",                           target = "id")
    @Mapping(source = "visit.faultDetails",                 target = "faultDetails")
    @Mapping(source = "visit.bookingDate",                  target = "bookingDate")
    @Mapping(source = "visit.acceptationDate",              target = "acceptationDate")
    @Mapping(source = "visit.expectedStartServiceDate",     target = "expectedStartServiceDate")
    @Mapping(source = "visit.expectedEndServiceDate",       target = "expectedEndServiceDate")
    @Mapping(source = "visit.client.email",                 target = "clientEmail")
    @Mapping(source = "visit.mechanic.email",               target = "mechanicEmail")
    @Mapping(source = "visit.car.model.name",               target = "carModel")
    VisitResponseDTO visitToVisitResponseDTO(Visit visit);

}
