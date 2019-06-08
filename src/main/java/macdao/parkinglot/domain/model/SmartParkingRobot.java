package macdao.parkinglot.domain.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class SmartParkingRobot implements ParkingRobot {

    private final ParkingLotId[] managedParkingLotIds;

    public SmartParkingRobot(ParkingLotId... managedParkingLotIds) {
        this.managedParkingLotIds = managedParkingLotIds;
    }

    @Override
    public List<ParkingLotId> getManagedParkingLotIds() {
        return Arrays.asList(managedParkingLotIds);
    }

    public Optional<ParkingLot> find(ParkingLot... parkingLots) {
        return Arrays.stream(parkingLots)
                .max(Comparator.comparingInt(ParkingLot::getSpace))
                .filter(ParkingLot::hasSpace);
    }
}
