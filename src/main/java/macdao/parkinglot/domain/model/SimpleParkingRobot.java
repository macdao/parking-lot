package macdao.parkinglot.domain.model;

import java.util.Arrays;
import java.util.Optional;

public class SimpleParkingRobot implements ParkingRobot {
    private final ParkingLot[] parkingLots;

    public SimpleParkingRobot(ParkingLot... parkingLots) {
        this.parkingLots = parkingLots;
    }

    @Override
    public Optional<ParkingLot> find() {
        return Arrays.stream(parkingLots)
                .filter(ParkingLot::hasSpace)
                .findFirst();
    }
}
