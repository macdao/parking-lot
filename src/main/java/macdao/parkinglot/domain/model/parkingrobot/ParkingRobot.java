package macdao.parkinglot.domain.model.parkingrobot;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.ParkingLotId;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ParkingRobot {

    private final ParkingPolicy parkingPolicy;
    private final ParkingLotId[] managedParkingLotIds;

    public ParkingRobot(ParkingPolicy parkingPolicy, ParkingLotId... managedParkingLotIds) {
        this.parkingPolicy = parkingPolicy;
        this.managedParkingLotIds = managedParkingLotIds;
    }

    public List<ParkingLotId> getManagedParkingLotIds() {
        return Arrays.asList(managedParkingLotIds);
    }


    public Optional<ParkingLot> find(ParkingLot... parkingLots) {
        return parkingPolicy.find(parkingLots);
    }
}
