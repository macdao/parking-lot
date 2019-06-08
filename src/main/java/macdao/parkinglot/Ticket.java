package macdao.parkinglot;

public class Ticket {
    private final ParkingLotId parkingLotId;
    private final TicketId id;

    public Ticket(ParkingLotId parkingLotId) {
        this.parkingLotId = parkingLotId;
        this.id = new TicketId();
    }

    public ParkingLotId getParkingLotId() {
        return parkingLotId;
    }
}
