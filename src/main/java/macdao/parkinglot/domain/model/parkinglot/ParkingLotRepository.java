package macdao.parkinglot.domain.model.parkinglot;

import java.util.Optional;

public interface ParkingLotRepository {
    Optional<ParkingLot> findById(ParkingLotId id);

    void save(ParkingLot parkingLot);
}
