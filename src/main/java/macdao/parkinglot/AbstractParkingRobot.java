package macdao.parkinglot;

import java.util.Arrays;

public abstract class AbstractParkingRobot implements ParkingRobot {
    final ParkingLot[] parkingLots;

    AbstractParkingRobot(ParkingLot... parkingLots) {
        this.parkingLots = parkingLots;
    }

    @Override
    public boolean canPark() {
        return Arrays.stream(parkingLots)
                .anyMatch(ParkingLot::hasSpace);
    }
}
