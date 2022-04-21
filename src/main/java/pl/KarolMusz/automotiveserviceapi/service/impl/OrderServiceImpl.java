package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.CarPartDTO;
import pl.KarolMusz.automotiveserviceapi.mapper.CarMapper;
import pl.KarolMusz.automotiveserviceapi.model.CarPart;
import pl.KarolMusz.automotiveserviceapi.repository.CarPartRepository;
import pl.KarolMusz.automotiveserviceapi.service.OrderService;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {

    private final CarPartRepository carPartRepository;
    private final CarMapper carMapper;

    @Override
    public List<CarPartDTO> getAllCarParts() {
        return carPartRepository.findAll()
                .stream()
                    .map(carMapper::carPartToCardPartDTO)
                    .toList();
    }

    @Override
    public CarPartDTO saveCarPart(CarPartDTO carPartDTO) {

        Optional<CarPart> carPartOptional = carPartRepository.getCarPartByCode(carPartDTO.code);

        CarPart carPart;
        if(carPartOptional.isEmpty()) {
            carPart = CarPart.builder()
                    .code(carPartDTO.code)
                    .name(carPartDTO.name)
                    .price(carPartDTO.price)
                    .quantity(carPartDTO.quantity)
                    .build();

            carPartRepository.save(carPart);
        }
        else {
            carPart = carPartOptional.get();
            carPart.setName(carPartDTO.name);
            carPart.setPrice(carPartDTO.price);
            carPart.setQuantity(carPart.getQuantity() + carPartDTO.quantity);
        }

        return carMapper.carPartToCardPartDTO(carPart);
    }

    @Override
    public void deletePart(String partCode) {
        Optional<CarPart> carPartOptional = carPartRepository.getCarPartByCode(partCode);
        carPartOptional.ifPresent(carPartRepository::delete);
    }
}
