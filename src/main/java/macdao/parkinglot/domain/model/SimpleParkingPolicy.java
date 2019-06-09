package macdao.parkinglot.domain.model;

import java.util.Arrays;
import java.util.Optional;

public class SimpleParkingPolicy implements ParkingPolicy {
    @Override
    public Optional<ParkingLot> find(ParkingLot... parkingLots) {
        return Arrays.stream(parkingLots)
                .filter(ParkingLot::hasSpace)
                .findFirst();
    }
}
