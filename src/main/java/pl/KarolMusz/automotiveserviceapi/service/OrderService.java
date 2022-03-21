package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.OrderCreateRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.OrderDTO;
import pl.KarolMusz.automotiveserviceapi.dto.OrderStatusDTO;
import pl.KarolMusz.automotiveserviceapi.dto.PartDTO;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    List<OrderDTO> findAllUnfinishedOrders();
    OrderDTO setNewOrderStatus(OrderStatusDTO orderStatusDTO) throws Exception;
    OrderDTO createNewOrder(OrderCreateRequestDTO orderDTO) throws Exception;
    OrderDTO addPartToOrder(UUID id, PartDTO partDTO) throws Exception;
    OrderDTO removePartFromOrder(UUID orderId, UUID partId) throws Exception;
}
