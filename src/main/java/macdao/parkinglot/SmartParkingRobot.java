package macdao.parkinglot;

import java.util.Arrays;
import java.util.Comparator;

public class SmartParkingRobot extends AbstractParkingRobot {

    public SmartParkingRobot(ParkingLot... parkingLots) {
        super(parkingLots);
    }

    public Ticket park(Car car) {
        return Arrays.stream(parkingLots)
                .max(Comparator.comparingInt(ParkingLot::getSpace))
                .map(p -> p.park(car))
                .orElseThrow(RuntimeException::new);
    }
}
