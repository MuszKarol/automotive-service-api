package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.CarDTO;
import pl.KarolMusz.automotiveserviceapi.model.Car;

@Mapper
public interface CarMapper {

    @Mapping(source = "car.vinCode",            target = "vinCode")
    @Mapping(source = "car.licensePlate",       target = "licensePlate")
    @Mapping(source = "car.model.name",         target = "modelName")
    @Mapping(source = "car.model.brand.name",   target = "brandName")
    CarDTO carToCarDTO(Car car);
}
