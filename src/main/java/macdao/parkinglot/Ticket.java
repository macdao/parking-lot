package macdao.parkinglot;

public class Ticket {
    private final ParkingLotId parkingLotId;

    public Ticket(ParkingLotId parkingLotId) {
        this.parkingLotId = parkingLotId;
    }

    public ParkingLotId getParkingLotId() {
        return parkingLotId;
    }
}
