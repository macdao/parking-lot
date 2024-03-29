package macdao.parkinglot.adapter.mem;

import macdao.parkinglot.domain.model.parkinglot.ParkingLotRepository;
import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.ParkingLotId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemParkingLotRepository implements ParkingLotRepository {
    private List<ParkingLot> parkingLotList = new ArrayList<>();

    public Optional<ParkingLot> findById(ParkingLotId id) {
        return parkingLotList.stream()
                .filter(p -> p.getId().equals(id))
                .findFirst();
    }

    public void save(ParkingLot parkingLot) {
        parkingLotList.add(parkingLot);
    }

}
