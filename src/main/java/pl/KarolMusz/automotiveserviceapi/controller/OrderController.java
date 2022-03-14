package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.OrderDTO;
import pl.KarolMusz.automotiveserviceapi.dto.OrderStatusDTO;
import pl.KarolMusz.automotiveserviceapi.dto.PartDTO;
import pl.KarolMusz.automotiveserviceapi.service.OrderService;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RequestMapping("/orders")
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        return ResponseEntity.ok().body(orderService.findAllUnfinishedOrders());
    }

    @PatchMapping
    public ResponseEntity<OrderDTO> changeOrderStatus(@RequestBody OrderStatusDTO orderStatusDTO) {
        try {
            return ResponseEntity.ok().body(orderService.setNewOrderStatus(orderStatusDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/new")
    public ResponseEntity<OrderDTO> createNewOrder(@RequestBody OrderDTO orderDTO) {
        try {
            return ResponseEntity.ok().body(orderService.createNewOrder(orderDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/{id}/products/new")
    public ResponseEntity<OrderDTO> addNewProduct(@PathVariable("id") UUID orderId, @RequestBody PartDTO partDTO) {
        try {
            return ResponseEntity.ok().body(orderService.addPartToOrder(orderId, partDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{order_id}/products/{product_id}")
    public ResponseEntity<OrderDTO> removeProduct(@PathVariable("order_id") UUID orderId,
                                        @PathVariable("product_id") UUID productId)
    {
        try {
            return ResponseEntity.ok().body(orderService.removePartFromOrder(orderId, productId));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}
