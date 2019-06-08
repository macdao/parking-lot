package macdao.parkinglot.domain;

import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.ParkingLotId;

import java.util.Optional;

public interface ParkingLotRepository {
    Optional<ParkingLot> findById(ParkingLotId id);

    void save(ParkingLot parkingLot);
}
