package macdao.parkinglot.application;

import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.ParkingRobot;
import macdao.parkinglot.domain.model.Ticket;

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
