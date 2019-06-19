package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.CarNumber;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.Ticket;
import macdao.parkinglot.domain.service.RobotFinder;
import org.springframework.stereotype.Service;

@Service
public class ParkingApplicationService {
    private final ParkingLotRepository parkingLotRepository;
    private final RobotFinder robotFinder;

    public ParkingApplicationService(ParkingLotRepository parkingLotRepository, RobotFinder robotFinder) {
        this.parkingLotRepository = parkingLotRepository;
        this.robotFinder = robotFinder;
    }

    public Ticket park(ParkCommand parkCommand) {
        final Car car = new Car(new CarNumber(parkCommand.getCarNumber()));

        final ParkingLot parkingLot = robotFinder.find().orElseThrow(ParkingLotIsFullException::new);

        final Ticket ticket = parkingLot.park(car);
        parkingLotRepository.save(parkingLot);
        return ticket;
    }
}
