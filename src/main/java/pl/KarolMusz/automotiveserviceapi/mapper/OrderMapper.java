package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.OrderDTO;
import pl.KarolMusz.automotiveserviceapi.dto.PartDTO;
import pl.KarolMusz.automotiveserviceapi.model.Order;
import pl.KarolMusz.automotiveserviceapi.model.Part;
import pl.KarolMusz.automotiveserviceapi.model.Quantity;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Mapping(source = "order.id",                   target = "orderId")
    @Mapping(source = "order.status",               target = "status")
    @Mapping(source = "order.creator.email",        target = "userEmail")
    @Mapping(source = "partDTOList",                target = "parts")
    OrderDTO orderToOrderDTO(Order order, List<PartDTO> partDTOList);

    @Mapping(source = "quantity.part.code",         target = "code")
    @Mapping(source = "quantity.part.name",         target = "name")
    @Mapping(source = "quantity.part.price",        target = "price")
    @Mapping(source = "quantity.number",            target = "quantity")
    PartDTO quantityToPartDTO(Quantity quantity);

    @Mapping(source = "partDTO.code",               target = "code")
    @Mapping(source = "partDTO.name",               target = "name")
    @Mapping(source = "partDTO.price",              target = "price")
    Part partDtoToPart(PartDTO partDTO);

    @Mapping(source = "partDTO.quantity",           target = "number")
    @Mapping(source = "part",                       target = "part")
    Quantity partDtoToQuantity(PartDTO partDTO, Part part);
}
