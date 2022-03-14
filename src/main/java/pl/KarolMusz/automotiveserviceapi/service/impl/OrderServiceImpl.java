package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.OrderDTO;
import pl.KarolMusz.automotiveserviceapi.dto.OrderStatusDTO;
import pl.KarolMusz.automotiveserviceapi.dto.PartDTO;
import pl.KarolMusz.automotiveserviceapi.mapper.OrderMapper;
import pl.KarolMusz.automotiveserviceapi.model.Order;
import pl.KarolMusz.automotiveserviceapi.model.Part;
import pl.KarolMusz.automotiveserviceapi.model.User;
import pl.KarolMusz.automotiveserviceapi.model.enums.OrderStatus;
import pl.KarolMusz.automotiveserviceapi.repository.OrderRepository;
import pl.KarolMusz.automotiveserviceapi.repository.PartRepository;
import pl.KarolMusz.automotiveserviceapi.repository.UserRepository;
import pl.KarolMusz.automotiveserviceapi.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> findAllUnfinishedOrders() {
        List<Order> orders =  orderRepository.findAllByStatus(OrderStatus.APPROVED);
        orders.addAll(orderRepository.findAllByStatus(OrderStatus.UNAPPROVED));

        return orders.stream()
                .map(this::mapToOrderDTO)
                .toList();
    }

    @Override
    public OrderDTO setNewOrderStatus(OrderStatusDTO orderStatusDTO) throws Exception {
        Order order = getOrderByUUID(orderStatusDTO.orderId);

        order.setStatus(OrderStatus.valueOf(orderStatusDTO.status));
        orderRepository.save(order);

        return mapToOrderDTO(order);
    }

    @Override
    public OrderDTO createNewOrder(OrderDTO orderDTO) throws Exception {

        List<Part> parts = orderDTO.parts.stream().map(orderMapper::partDtoToPart).toList();
        partRepository.saveAll(parts);

        Optional<User> userOptional = userRepository.getUserByEmail(orderDTO.userEmail); //TODO

        if (userOptional.isEmpty())
            throw new Exception();  //TODO

        Order order = Order.builder()
                .status(OrderStatus.valueOf(orderDTO.status))
                .creator(userOptional.get())
                .parts(parts)
                .build();

        orderRepository.save(order);

        return mapToOrderDTO(order);
    }

    @Override
    public OrderDTO addPartToOrder(UUID id, PartDTO partDTO) throws Exception {
        Order order = getOrderByUUID(id);

        Part newPart = orderMapper.partDtoToPart(partDTO);

        List<Part> parts = order.getParts();
        parts.add(newPart);

        order.setParts(parts);
        orderRepository.save(order);

        return mapToOrderDTO(order);
    }

    @Override
    public OrderDTO removePartFromOrder(UUID orderId, UUID partId) throws Exception {
        Order order = getOrderByUUID(orderId);

        List<Part> parts = order.getParts().stream()
                .filter(part -> !part.getId().equals(partId))
                .toList();

        order.setParts(parts);
        orderRepository.save(order);

        return mapToOrderDTO(order);
    }

    private Order getOrderByUUID(UUID id) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty())
            throw new Exception(); //TODO

        return orderOptional.get();
    }

    private List<PartDTO> mapToPartDTOList(List<Part> parts) {
        return parts.stream()
                .map(orderMapper::partToPartDTO)
                .toList();
    }

    private OrderDTO mapToOrderDTO(Order order) {
        return orderMapper.orderToOrderDTO(order, mapToPartDTOList(order.getParts()));
    }
}
