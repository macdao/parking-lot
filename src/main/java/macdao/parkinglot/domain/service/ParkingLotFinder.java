package macdao.parkinglot.domain.service;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.ParkingLotRepository;
import macdao.parkinglot.domain.model.parkingrobot.ParkingRobotRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
public class ParkingLotFinder {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingRobotRepository parkingRobotRepository;

    public ParkingLotFinder(ParkingLotRepository parkingLotRepository, ParkingRobotRepository parkingRobotRepository) {
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