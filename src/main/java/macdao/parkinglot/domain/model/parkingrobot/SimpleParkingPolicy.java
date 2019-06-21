package macdao.parkinglot.domain.model.parkingrobot;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;

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
