package macdao.parkinglot.domain.model.parkingrobot;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Optional;

public class SmartParkingPolicy implements ParkingPolicy {
    @Override
    public Optional<ParkingLot> find(ParkingLot... parkingLots) {
        return Arrays.stream(parkingLots)
                .max(Comparator.comparingInt(ParkingLot::getSpace))
                .filter(ParkingLot::hasSpace);
    }
}
