package macdao.parkinglot;

import java.util.Arrays;

public class ParkingManager {
    private final ParkingRobot[] parkingRobots;

    public ParkingManager(ParkingRobot... parkingRobots) {
        this.parkingRobots = parkingRobots;
    }

    public Ticket park(Car car) {
        return Arrays.stream(parkingRobots)
                .filter(ParkingRobot::canPark)
                .findFirst()
                .map(r -> r.park(car))
                .orElseThrow(ParkingLotIsFullException::new);
    }
}
