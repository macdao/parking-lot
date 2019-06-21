package macdao.parkinglot.domain.model.parkinglot;

import lombok.Data;

@Data
public class Ticket {
    private final TicketId id;
    private final ParkingLotId parkingLotId;
    private final CarNumber carNumber;

    public Ticket(ParkingLotId parkingLotId, CarNumber carNumber) {
        this.parkingLotId = parkingLotId;
        this.carNumber = carNumber;
        this.id = new TicketId();
    }
}
