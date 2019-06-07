package macdao.parkinglot;

import java.util.Arrays;
import java.util.Optional;

public class ParkingManager {
    private ParkingRobot[] parkingRobots;

    public ParkingManager(ParkingRobot... parkingRobots) {
        this.parkingRobots = parkingRobots;
    }

    public Ticket park(Car car) {
        return find()
                .map(p -> p.park(car))
                .orElseThrow(ParkingLotIsFullException::new);
    }

    private Optional<ParkingLot> find() {
        return Arrays.stream(parkingRobots)
                .map(ParkingRobot::find)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}
