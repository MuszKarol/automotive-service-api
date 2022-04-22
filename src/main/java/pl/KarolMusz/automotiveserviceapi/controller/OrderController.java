package pl.KarolMusz.automotiveserviceapi.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.KarolMusz.automotiveserviceapi.dto.CarPartDTO;
import pl.KarolMusz.automotiveserviceapi.service.OrderService;

import java.util.List;

@AllArgsConstructor
@RequestMapping("/order/parts")
@RestController
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<CarPartDTO>> getParts() {
        return ResponseEntity.ok().body(orderService.getAllCarParts());
    }

    @PostMapping
    public ResponseEntity<CarPartDTO> savePart(@RequestBody CarPartDTO carPartDTO) {
        return ResponseEntity.ok().body(orderService.saveCarPart(carPartDTO));
    }

    @DeleteMapping("/{partCode}")
    public ResponseEntity<?> deletePart(@PathVariable String partCode) {
        orderService.deletePart(partCode);
        return ResponseEntity.ok().build();
    }
}
