package macdao.parkinglot.domain.model;

import java.util.List;
import java.util.Optional;

public interface ParkingRobot {
    List<ParkingLotId> getManagedParkingLotIds();
    Optional<ParkingLot> find(ParkingLot... parkingLots);
}
