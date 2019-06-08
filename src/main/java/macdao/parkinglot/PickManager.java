package macdao.parkinglot;

import java.util.Arrays;

public class PickManager {
    private final TicketRepository ticketRepository;
    private final ParkingLot[] parkingLots;

    public PickManager(TicketRepository ticketRepository, ParkingLot... parkingLots) {
        this.ticketRepository = ticketRepository;
        this.parkingLots = parkingLots;
    }

    public Car pick(TicketId ticketId) {
        final Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(TicketInvalidException::new);

        return Arrays.stream(parkingLots)
                .filter(p -> p.getId().equals(ticket.getParkingLotId()))
                .findFirst()
                .map(p -> p.pick(ticket.getCarNumber()))
                .orElseThrow(TicketInvalidException::new);
    }
}
