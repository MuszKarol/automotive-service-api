package pl.KarolMusz.automotiveserviceapi.service.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import pl.KarolMusz.automotiveserviceapi.dto.VisitPatchRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitRequestDTO;
import pl.KarolMusz.automotiveserviceapi.dto.VisitResponseDTO;
import pl.KarolMusz.automotiveserviceapi.mapper.VisitMapper;
import pl.KarolMusz.automotiveserviceapi.model.Car;
import pl.KarolMusz.automotiveserviceapi.model.User;
import pl.KarolMusz.automotiveserviceapi.model.Visit;
import pl.KarolMusz.automotiveserviceapi.model.enums.ServiceStatus;
import pl.KarolMusz.automotiveserviceapi.repository.UserRepository;
import pl.KarolMusz.automotiveserviceapi.repository.VisitRepository;
import pl.KarolMusz.automotiveserviceapi.service.VisitService;

import javax.persistence.EntityNotFoundException;
import java.sql.Date;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class VisitServiceImpl implements VisitService {

    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final VisitMapper visitMapper;

    @Override
    public List<VisitResponseDTO> getAllUserVisits() {
        List<Visit> visits = visitRepository.getAllByClient(UserServiceImpl.getUserFromContext(userRepository));

        return checkEndOfServiceDate(visits).stream()
                .map(visitMapper::visitToVisitResponseDTO)
                .sorted(Comparator.comparing(visit -> visit.carDeliveryDate))
                .toList();
    }

    @Override
    public List<VisitResponseDTO> getAllVisitsWithStatusUnfinished() {
        List<Visit> visits = visitRepository.getAllByServiceStatus(ServiceStatus.ACCEPTED);
        visits.addAll(visitRepository.getAllByServiceStatus(ServiceStatus.ACTIVE));

        return checkEndOfServiceDate(visits).stream()
                .map(visitMapper::visitToVisitResponseDTO)
                .sorted(Comparator.comparing(visit -> visit.carDeliveryDate))
                .toList();
    }

    @Override
    public List<VisitResponseDTO> getAllVisitsWithStatusNew() {
        List<Visit> visits = visitRepository.getAllByServiceStatus(ServiceStatus.NEW);

        return visits.stream()
                .map(visitMapper::visitToVisitResponseDTO)
                .sorted(Comparator.comparing(visit -> visit.carDeliveryDate))
                .toList();
    }

    @Override
    public VisitResponseDTO createNewVisit(VisitRequestDTO visitRequestDTO) throws EntityNotFoundException {
        User client = UserServiceImpl.getUserFromContext(userRepository);

        Optional<Car> carOptional = getUserCar(visitRequestDTO, client);

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
        User client = UserServiceImpl.getUserFromContext(userRepository);
        Visit visit;

        if (visitOptional.isEmpty()) {
            throw new EntityNotFoundException("Visit not found");
        }

        if (visitOptional.get().getClient().equals(client)) {
            visit = modifyVisitStatus(visitPatchRequestDTO, visitOptional.get());
        } else {
            visit = updateVisit(visitPatchRequestDTO, visitOptional.get());
        }

        return visitMapper.visitToVisitResponseDTO(visitRepository.save(visit));
    }

    private Optional<Car> getUserCar(VisitRequestDTO visitRequestDTO, User client) {
        return client.getListOfCars().stream()
                .filter(x -> x.getVinCode().equals(visitRequestDTO.vinCode))
                .findFirst();
    }

    private Visit modifyVisitStatus(VisitPatchRequestDTO visitPatchRequestDTO, Visit visit) {
        if (visitPatchRequestDTO.serviceStatus.equals("REJECTED")) {
            return updateVisit(visitPatchRequestDTO, visit);
        } else {
            return visit;
        }
    }

    private Visit updateVisit(VisitPatchRequestDTO visitPatchDTO, Visit visit) {
        visit.setServiceStatus(ServiceStatus.valueOf(visitPatchDTO.serviceStatus));

        if (ServiceStatus.ACCEPTED.toString().equals(visitPatchDTO.serviceStatus)) {
            visit.setAcceptationDate(new Date(System.currentTimeMillis()));
        }
        if (visitPatchDTO.carDeliveryDate != null) {
            visit.setCarDeliveryDate(visitPatchDTO.carDeliveryDate);
        }
        if (visitPatchDTO.expectedStartServiceDate != null) {
            visit.setExpectedStartServiceDate(visitPatchDTO.expectedStartServiceDate);
        }
        if (visitPatchDTO.expectedEndServiceDate != null) {
            visit.setExpectedEndServiceDate(visitPatchDTO.expectedEndServiceDate);
        }

        return visit;
    }

    private List<Visit> checkEndOfServiceDate(List<Visit> visits) {
        Date currentDate = new Date(System.currentTimeMillis());

        for (Visit visit : visits) {
            if ((visit.getExpectedEndServiceDate() != null) &&
                    (visit.getExpectedEndServiceDate().compareTo(currentDate) < 0)) {
                visit.setExpectedEndServiceDate(currentDate);
                visitRepository.save(visit);
            }
        }

        return visits;
    }
}
