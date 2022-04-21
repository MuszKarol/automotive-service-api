package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.CarDTO;
import pl.KarolMusz.automotiveserviceapi.dto.CarPartDTO;
import pl.KarolMusz.automotiveserviceapi.model.Car;
import pl.KarolMusz.automotiveserviceapi.model.CarPart;

@Mapper
public interface CarMapper {

    @Mapping(source = "car.vinCode",            target = "vinCode")
    @Mapping(source = "car.licensePlate",       target = "licensePlate")
    @Mapping(source = "car.model.modelName",    target = "modelName")
    @Mapping(source = "car.model.brandName",    target = "brandName")
    CarDTO carToCarDTO(Car car);

    @Mapping(source = "carPart.code",       target = "code")
    @Mapping(source = "carPart.name",       target = "name")
    @Mapping(source = "carPart.price",      target = "price")
    @Mapping(source = "carPart.quantity",   target = "quantity")
    CarPartDTO carPartToCardPartDTO(CarPart carPart);
}
