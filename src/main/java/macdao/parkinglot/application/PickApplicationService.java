package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.exception.TicketInvalidException;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.Ticket;
import macdao.parkinglot.domain.model.TicketId;
import org.springframework.stereotype.Service;

@Service
public class PickApplicationService {
    private final TicketRepository ticketRepository;
    private final ParkingLotRepository parkingLotRepository;

    public PickApplicationService(TicketRepository ticketRepository, ParkingLotRepository parkingLotRepository) {
        this.ticketRepository = ticketRepository;
        this.parkingLotRepository = parkingLotRepository;
    }

    public Car pick(PickCommand pickCommand) {
        final TicketId ticketId = new TicketId(pickCommand.getTicketId());
        final Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketInvalidException::new);

        ticketRepository.delete(ticket);

        final ParkingLot parkingLot = parkingLotRepository.findById(ticket.getParkingLotId())
                .orElseThrow(TicketInvalidException::new);

        parkingLotRepository.save(parkingLot);
        return parkingLot.pick(ticket.getCarNumber());
    }
}
