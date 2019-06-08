package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.Ticket;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ParkingApplicationService {
    private final TicketRepository ticketRepository;
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingRobotRepository parkingRobotRepository;

    public ParkingApplicationService(TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository, ParkingRobotRepository parkingRobotRepository) {
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
        this.parkingRobotRepository = parkingRobotRepository;
    }

    public Ticket park(Car car) {
        return find()
                .map(p -> {
                    p.park(car);
                    final Ticket ticket = new Ticket(p.getId(), car.getCarNumber());
                    ticketRepository.save(ticket);
                    return ticket;
                })
                .orElseThrow(ParkingLotIsFullException::new);
    }

    private Optional<ParkingLot> find() {
        return StreamSupport.stream(parkingRobotRepository.findAll().spliterator(), false)
                .map(r -> {
                    final ParkingLot[] parkingLots = r.getManagedParkingLotIds().stream()
                            .map(id -> parkingLotRepository.findById(id).orElseThrow(RuntimeException::new))
                            .toArray(ParkingLot[]::new);
                    return r.find(parkingLots);
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
