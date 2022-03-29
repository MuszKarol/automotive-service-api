package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.*;
import pl.KarolMusz.automotiveserviceapi.mapper.OrderMapper;
import pl.KarolMusz.automotiveserviceapi.model.Order;
import pl.KarolMusz.automotiveserviceapi.model.Part;
import pl.KarolMusz.automotiveserviceapi.model.Quantity;
import pl.KarolMusz.automotiveserviceapi.model.User;
import pl.KarolMusz.automotiveserviceapi.model.enums.OrderStatus;
import pl.KarolMusz.automotiveserviceapi.repository.OrderRepository;
import pl.KarolMusz.automotiveserviceapi.repository.PartRepository;
import pl.KarolMusz.automotiveserviceapi.repository.QuantityRepository;
import pl.KarolMusz.automotiveserviceapi.repository.UserRepository;
import pl.KarolMusz.automotiveserviceapi.service.OrderService;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final QuantityRepository quantityRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;
    private final OrderMapper orderMapper;

    @Override
    public List<OrderDTO> findAllUnfinishedOrders() {
        List<Order> orders =  orderRepository.findAllByStatus(OrderStatus.APPROVED);
        orders.addAll(orderRepository.findAllByStatus(OrderStatus.UNAPPROVED));
        orders.addAll(orderRepository.findAllByStatus(OrderStatus.NEW));

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
    public OrderDTO createNewOrder(OrderCreateRequestDTO orderDTO) throws Exception {
        List<Quantity> quantityListWithParts = quantityRepository.saveAll(
                orderDTO.parts.stream()
                        .map(this::mapPartDtoToQuantity)
                        .toList()
        );

        Optional<User> userOptional = userRepository.getUserByEmail(orderDTO.userEmail); //TODO

        if (userOptional.isEmpty())
            throw new Exception();  //TODO

        Order order = Order.builder()
                .status(OrderStatus.valueOf(orderDTO.status))
                .creator(userOptional.get())
                .numberOfPartsList(quantityListWithParts)
                .build();

        return mapToOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO addPartToOrder(UUID id, PartDTO partDTO) throws Exception {
        Order order = getOrderByUUID(id);
        Quantity quantity;

        Optional<Part> partOptional = partRepository.getPartByCode(partDTO.code);

        if (partOptional.isEmpty()) {
            quantity = orderMapper.partDtoToQuantity(partDTO,
                    partRepository.save(orderMapper.partDtoToPart(partDTO))
            );

            quantityRepository.save(quantity);
        }
        else {
            order.getNumberOfPartsList().stream()
                    .filter(quantity1 -> quantity1.getPart().getCode().equals(partDTO.code))
                    .findFirst()
                    .ifPresent(quantity2 -> quantity2.setNumber(quantity2.getNumber() + partDTO.quantity));

            return mapToOrderDTO(order);
        }

        List<Quantity> numberOfPartsList = order.getNumberOfPartsList();
        numberOfPartsList.add(quantity);
        order.setNumberOfPartsList(numberOfPartsList);

        return mapToOrderDTO(orderRepository.save(order));
    }

    @Override
    public OrderDTO removePartFromOrder(UUID orderId, UUID partId) throws Exception {
        Order order = getOrderByUUID(orderId);

        Optional<Quantity> quantityToRemoveOptional = order.getNumberOfPartsList().stream()
                .filter(quantity -> quantity.getPart().getId().equals(partId))
                .findFirst();

        if (quantityToRemoveOptional.isEmpty())
            throw new Exception();      //TODO

        List<Quantity> numberOfPartsList = order.getNumberOfPartsList();
        numberOfPartsList.remove(quantityToRemoveOptional.get());
        order.setNumberOfPartsList(numberOfPartsList);

        return mapToOrderDTO(orderRepository.save(order));
    }

    private Order getOrderByUUID(UUID id) throws Exception {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty())
            throw new Exception(); //TODO

        return orderOptional.get();
    }

    private List<PartResponseDTO> mapToQuantityList(List<Quantity> quantities) {
        return quantities.stream()
                .map(orderMapper::quantityToPartDTO)
                .toList();
    }

    private OrderDTO mapToOrderDTO(Order order) {
        return orderMapper.orderToOrderDTO(order, mapToQuantityList(order.getNumberOfPartsList()));
    }

    private Quantity mapPartDtoToQuantity(PartDTO partDTO) {
        return orderMapper.partDtoToQuantity(partDTO, createNewPart(partDTO));
    }

    private Part createNewPart(PartDTO partDTO) {
        Optional<Part> partOptional = partRepository.getPartByCode(partDTO.code);
        Part part;

        if (partOptional.isPresent()) {
            part = partOptional.get();
            part.setPrice(partDTO.price);
            part.setName(partDTO.name);
            partRepository.save(part);
        }
        else {
             part = partRepository.save(orderMapper.partDtoToPart(partDTO));
        }

        return part;
    }
}
