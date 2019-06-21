package macdao.parkinglot.domain.model.parkingrobot;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;

import java.util.Optional;

public interface ParkingPolicy {
    Optional<ParkingLot> find(ParkingLot... parkingLots);
}
