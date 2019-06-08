package macdao.parkinglot.domain.model;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class SimpleParkingRobot implements ParkingRobot {

    private final ParkingLotId[] managedParkingLotIds;

    public SimpleParkingRobot(ParkingLotId... managedParkingLotIds) {
        this.managedParkingLotIds = managedParkingLotIds;
    }

    @Override
    public List<ParkingLotId> getManagedParkingLotIds() {
        return Arrays.asList(managedParkingLotIds);
    }

    @Override
    public Optional<ParkingLot> find(ParkingLot... parkingLots) {
        return Arrays.stream(parkingLots)
                .filter(ParkingLot::hasSpace)
                .findFirst();
    }
}
