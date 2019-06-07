package macdao.parkinglot;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class SmartParkingRobot implements ParkingRobot {
    private final ParkingLot[] parkingLots;

    public SmartParkingRobot(ParkingLot... parkingLots) {
        this.parkingLots = parkingLots;
    }

    public Optional<ParkingLot> find() {
        return Arrays.stream(parkingLots)
                .max(Comparator.comparingInt(ParkingLot::getSpace))
                .filter(ParkingLot::hasSpace);
    }
}
