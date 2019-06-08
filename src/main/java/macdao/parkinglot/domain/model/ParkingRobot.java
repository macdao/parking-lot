package macdao.parkinglot.domain.model;

import java.util.Optional;

public interface ParkingRobot {
    Optional<ParkingLot> find();
}
