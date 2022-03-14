package pl.KarolMusz.automotiveserviceapi.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.KarolMusz.automotiveserviceapi.dto.OrderDTO;
import pl.KarolMusz.automotiveserviceapi.dto.PartDTO;
import pl.KarolMusz.automotiveserviceapi.model.Order;
import pl.KarolMusz.automotiveserviceapi.model.Part;
import pl.KarolMusz.automotiveserviceapi.model.User;

import java.util.List;

@Mapper
public interface OrderMapper {

    @Mapping(source = "order.id",                   target = "orderId")
    @Mapping(source = "order.status",               target = "status")
    @Mapping(source = "order.creator.email",        target = "userEmail")
    @Mapping(source = "partDTOList",                target = "parts")
    OrderDTO orderToOrderDTO(Order order, List<PartDTO> partDTOList);

    @Mapping(source = "part.code",      target = "code")
    @Mapping(source = "part.name",      target = "name")
    @Mapping(source = "part.price",     target = "price")
    PartDTO partToPartDTO(Part part);

    @Mapping(source = "partDTO.code",   target = "code")
    @Mapping(source = "partDTO.name",   target = "name")
    @Mapping(source = "partDTO.price",  target = "price")
    Part partDtoToPart(PartDTO partDTO);

    @Mapping(source = "orderDTO.status",    target = "status")
    @Mapping(source = "user",               target = "creator")
    @Mapping(source = "partList",           target = "parts")
    Order orderToOrderDTO(OrderDTO orderDTO, User user, List<Part> partList);
}
