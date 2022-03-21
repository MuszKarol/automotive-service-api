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
import pl.KarolMusz.automotiveserviceapi.service.BookingService;

import java.sql.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@AllArgsConstructor
@Service
public class BookingServiceImpl implements BookingService {

    private final UserRepository userRepository;
    private final VisitRepository visitRepository;
    private final CarRepository carRepository;
    private final VisitMapper visitMapper;

    @Override
    public VisitResponseDTO getVisitDetailsUsingId(UUID visitId) throws Exception {

        Optional<Visit> visitOptional = visitRepository.findById(visitId);

        if (visitOptional.isEmpty())
            throw new Exception();  //TODO

        return visitMapper.visitToVisitResponseDTO(visitOptional.get());
    }

    @Override
    public List<VisitResponseDTO> getAllVisitsWithStatusUncompleted() {
        List<Visit> visits = visitRepository.getAllByServiceStatusAndServiceStatusAndServiceStatus(ServiceStatus.NEW,
                ServiceStatus.ACTIVE, ServiceStatus.ACCEPTED);

        return visits.stream().map(visitMapper::visitToVisitResponseDTO).toList();
    }

    @Override
    public VisitResponseDTO createNewVisit(VisitRequestDTO visitRequestDTO) throws Exception {
        Optional<User> clientOptional = userRepository.getUserByEmail(visitRequestDTO.clientEmail);
        Optional<User> mechanicOptional = userRepository.getUserByEmail(visitRequestDTO.mechanicEmail);

        if (mechanicOptional.isEmpty() || clientOptional.isEmpty())
            throw new Exception();  //TODO

        Optional<Car> carOptional = carRepository.getCarByVinCode(visitRequestDTO.vinCode);

        if (carOptional.isEmpty())
            throw new Exception();  //TODO

        Visit visit = Visit.builder()
                .client(clientOptional.get())
                .mechanic(mechanicOptional.get()) /*TODO*/
                .car(carOptional.get())
                .bookingDate(visitRequestDTO.bookingDate)
                .acceptationDate(visitRequestDTO.acceptationDate)
                .expectedStartServiceDate(visitRequestDTO.expectedStartServiceDate)
                .expectedEndServiceDate(visitRequestDTO.expectedEndServiceDate)
                .faultDetails(visitRequestDTO.faultDetails)
                .serviceStatus(ServiceStatus.NEW)
                .build();

        return visitMapper.visitToVisitResponseDTO(visitRepository.save(visit));
    }

    @Override
    public VisitResponseDTO updateVisitStatus(VisitPatchRequestDTO visitPatchRequestDTO) throws Exception {
        Optional<Visit> visitOptional = visitRepository.findById(visitPatchRequestDTO.visitId);

        if (visitOptional.isEmpty())
            throw new Exception(); //TODO

        Visit visit = visitOptional.get();

        visit.setServiceStatus(ServiceStatus.valueOf(visitPatchRequestDTO.visitStatus));
        visit.setAcceptationDate(new Date(System.currentTimeMillis()));
        visit.setExpectedStartServiceDate(visitPatchRequestDTO.expectedStartServiceDate);
        visit.setExpectedEndServiceDate(visitPatchRequestDTO.expectedEndServiceDate);

        Optional<User> mechanicOptional = userRepository.getUserByEmail(visitPatchRequestDTO.mechanicEmail);    //TODO
        mechanicOptional.ifPresent(visit::setMechanic);   //TODO

        return visitMapper.visitToVisitResponseDTO(visitRepository.save(visit));
    }
}
