package macdao.parkinglot;

import java.util.Arrays;

public class PickManager {
    private final ParkingLot[] parkingLots;

    public PickManager(ParkingLot... parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Car pick(Ticket ticket) {
        return Arrays.stream(parkingLots)
                .filter(p -> p.getId().equals(ticket.getParkingLotId()))
                .findFirst()
                .map(p -> p.pick(ticket))
                .orElseThrow(TicketInvalidException::new);
    }
}
