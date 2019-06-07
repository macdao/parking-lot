package macdao.parkinglot;

import java.util.Arrays;

public class SimpleParkingRobot extends AbstractParkingRobot {

    public SimpleParkingRobot(ParkingLot... parkingLots) {
        super(parkingLots);
    }

    public Ticket park(Car car) {
        return Arrays.stream(parkingLots)
                .filter(ParkingLot::hasSpace)
                .findFirst()
                .map(lot -> lot.park(car))
                .orElseThrow(ParkingLotIsFullException::new);
    }
}
