package macdao.parkinglot.application;

import macdao.parkinglot.domain.model.parkinglot.ParkingLotRepository;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;
import macdao.parkinglot.domain.model.parkinglot.Car;
import macdao.parkinglot.domain.model.parkinglot.CarNumber;
import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.Ticket;
import macdao.parkinglot.domain.service.ParkingManager;
import org.springframework.stereotype.Service;

@Service
public class ParkingApplicationService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingManager parkingManager;

    public ParkingApplicationService(ParkingLotRepository parkingLotRepository, ParkingManager parkingManager) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingManager = parkingManager;
    }

    public Ticket park(ParkCommand parkCommand) {
        final Car car = new Car(new CarNumber(parkCommand.getCarNumber()));

        final ParkingLot parkingLot = parkingManager.find().orElseThrow(ParkingLotIsFullException::new);

        final Ticket ticket = parkingLot.park(car);
        parkingLotRepository.save(parkingLot);
        return ticket;
    }
}
