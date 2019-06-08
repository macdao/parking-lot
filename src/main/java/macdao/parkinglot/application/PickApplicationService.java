package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.exception.TicketInvalidException;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.Ticket;
import macdao.parkinglot.domain.model.TicketId;

public class PickApplicationService {
    private final TicketRepository ticketRepository;
    private final ParkingLotRepository parkingLotRepository;

    public PickApplicationService(TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository) {
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public Car pick(TicketId ticketId) {
        final Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketInvalidException::new);

        ticketRepository.delete(ticket);

        return parkingLotRepository.findById(ticket.getParkingLotId())
                .map(p -> p.pick(ticket.getCarNumber()))
                .orElseThrow(TicketInvalidException::new);
    }
}
