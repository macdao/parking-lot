package macdao.parkinglot.domain.model;

public class Ticket {
    private final TicketId id;
    private final ParkingLotId parkingLotId;
    private final CarNumber carNumber;

    public Ticket(ParkingLotId parkingLotId, CarNumber carNumber) {
        this.parkingLotId = parkingLotId;
        this.carNumber = carNumber;
        this.id = new TicketId();
    }

    public TicketId getId() {
        return id;
    }

    public ParkingLotId getParkingLotId() {
        return parkingLotId;
    }

    public CarNumber getCarNumber() {
        return carNumber;
    }
}
