package macdao.parkinglot.domain.model;

import java.util.Optional;

public interface ParkingPolicy {
    Optional<ParkingLot> find(ParkingLot... parkingLots);
}
