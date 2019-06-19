package macdao.parkinglot.domain.service;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.model.ParkingLot;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class RobotFinder {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingRobotRepository parkingRobotRepository;

    public RobotFinder(ParkingLotRepository parkingLotRepository, ParkingRobotRepository parkingRobotRepository) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingRobotRepository = parkingRobotRepository;
    }

    public Optional<ParkingLot> find() {
        return StreamSupport.stream(parkingRobotRepository.findAll().spliterator(), false)
                .map(r -> {
                    final ParkingLot[] parkingLots = r.getManagedParkingLotIds().stream()
                            .map(id -> parkingLotRepository.findById(id).orElseThrow(RuntimeException::new))
                            .toArray(ParkingLot[]::new);
                    return r.find(parkingLots);
                })
                .filter(Optional::isPresent)
                .map(Optional::get)
                .findFirst();
    }
}