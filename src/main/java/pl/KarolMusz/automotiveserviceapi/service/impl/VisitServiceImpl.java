package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.VisitPatchRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;
import pl.KarolMusz.automotiveserviceapi.mapper.VisitMapper;
import pl.KarolMusz.automotiveserviceapi.model.User;
import pl.KarolMusz.automotiveserviceapi.model.Car;
import pl.KarolMusz.automotiveserviceapi.model.Visit;
import pl.KarolMusz.automotiveserviceapi.model.enums.ServiceStatus;
import pl.KarolMusz.automotiveserviceapi.repository.UserRepository;
import pl.KarolMusz.automotiveserviceapi.repository.CarRepository;
import pl.KarolMusz.automotiveserviceapi.repository.VisitRepository;
import pl.KarolMusz.automotiveserviceapi.service.VisitService;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {

    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final CarRepository carRepository;
    private final VisitMapper visitMapper;

    @Override
    public List<VisitResponseDTO> getAllUserVisits() {
        List<Visit> visits = visitRepository.getAllByClient(UserServiceImpl.getUserFromContext(userRepository));
        return visits.stream()
                .map(visitMapper::visitToVisitResponseDTO)
                .toList();
    }

    @Override
    public List<VisitResponseDTO> getAllVisitsWithStatusUnfinished() {
        List<Visit> visits = visitRepository.getAllByServiceStatus(ServiceStatus.ACCEPTED);
        visits.addAll(visitRepository.getAllByServiceStatus(ServiceStatus.ACTIVE));

        return visits.stream()
                .map(visitMapper::visitToVisitResponseDTO)
                .toList();
    }

    @Override
    public List<VisitResponseDTO> getAllVisitsWithStatusNew() {
        List<Visit> visits = visitRepository.getAllByServiceStatus(ServiceStatus.NEW);
        return visits.stream()
                .map(visitMapper::visitToVisitResponseDTO)
                .toList();
    }

    @Override
    public VisitResponseDTO createNewVisit(VisitRequestDTO visitRequestDTO) throws EntityNotFoundException {
        User client = UserServiceImpl.getUserFromContext(userRepository);

        Optional<Car> carOptional = carRepository.getCarByVinCode(visitRequestDTO.vinCode);

        if (carOptional.isEmpty()) {
            throw new EntityNotFoundException("Car not found");
        }

        Visit visit = Visit.builder()
                .client(client)
                .car(carOptional.get())
                .carDeliveryDate(visitRequestDTO.carDeliveryDate)
                .acceptationDate(visitRequestDTO.acceptationDate)
                .expectedStartServiceDate(visitRequestDTO.expectedStartServiceDate)
                .expectedEndServiceDate(visitRequestDTO.expectedEndServiceDate)
                .faultDetails(visitRequestDTO.faultDetails)
                .serviceStatus(ServiceStatus.NEW)
                .build();

        return visitMapper.visitToVisitResponseDTO(visitRepository.save(visit));
    }

    @Override
    public VisitResponseDTO updateVisitStatus(VisitPatchRequestDTO visitPatchRequestDTO) throws EntityNotFoundException {
        Optional<Visit> visitOptional = visitRepository.findById(visitPatchRequestDTO.id);

        if (visitOptional.isEmpty()) {
            throw new EntityNotFoundException("Visit not found");
        }

        Visit visit = visitOptional.get();

        visit.setServiceStatus(ServiceStatus.valueOf(visitPatchRequestDTO.serviceStatus));

        if (ServiceStatus.ACCEPTED.toString().equals(visitPatchRequestDTO.serviceStatus)) {
            visit.setAcceptationDate(new Date(System.currentTimeMillis()));
        }

        visit.setCarDeliveryDate(visitPatchRequestDTO.carDeliveryDate);
        visit.setExpectedStartServiceDate(visitPatchRequestDTO.expectedStartServiceDate);
        visit.setExpectedEndServiceDate(visitPatchRequestDTO.expectedEndServiceDate);

        return visitMapper.visitToVisitResponseDTO(visitRepository.save(visit));
    }
}
