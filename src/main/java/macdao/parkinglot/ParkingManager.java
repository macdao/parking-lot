package macdao.parkinglot;

import java.util.Arrays;
import java.util.Optional;

public class ParkingManager {
    private final TicketRepository ticketRepository;
    private ParkingRobot[] parkingRobots;

    public ParkingManager(TicketRepository ticketRepository, ParkingRobot... parkingRobots) {
        this.ticketRepository = ticketRepository;
        this.parkingRobots = parkingRobots;
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
        return Arrays.stream(parkingRobots)
                .map(ParkingRobot::find)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
