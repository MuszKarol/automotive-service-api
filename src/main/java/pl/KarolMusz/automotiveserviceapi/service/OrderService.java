package pl.KarolMusz.automotiveserviceapi.service;

import pl.KarolMusz.automotiveserviceapi.dto.CarPartDTO;

import java.util.List;

public interface OrderService {
    List<CarPartDTO> getAllCarParts();
    CarPartDTO saveCarPart(CarPartDTO carPartDTO);
    void deletePart(String partCode);
}
