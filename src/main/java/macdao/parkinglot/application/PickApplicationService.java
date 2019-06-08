package macdao.parkinglot.application;

import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.exception.TicketInvalidException;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.Ticket;
import macdao.parkinglot.domain.model.TicketId;

import java.util.Arrays;

public class PickApplicationService {
    private final TicketRepository ticketRepository;
    private final ParkingLot[] parkingLots;

    public PickApplicationService(TicketRepository ticketRepository, ParkingLot... parkingLots) {
        this.ticketRepository = ticketRepository;
        this.parkingLots = parkingLots;
    }

    public Car pick(TicketId ticketId) {
        final Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketInvalidException::new);

        ticketRepository.delete(ticket);

        return Arrays.stream(parkingLots)
                .filter(p -> p.getId().equals(ticket.getParkingLotId()))
                .findFirst()
                .map(p -> p.pick(ticket.getCarNumber()))
                .orElseThrow(TicketInvalidException::new);
    }
}
