package macdao.parkinglot.application;

import macdao.parkinglot.domain.model.parkinglot.ParkingLotRepository;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;
import macdao.parkinglot.domain.model.parkinglot.Car;
import macdao.parkinglot.domain.model.parkinglot.CarNumber;
import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.Ticket;
import macdao.parkinglot.domain.service.ParkingLotFinder;
import org.springframework.stereotype.Service;

@Service
public class ParkingApplicationService {
    private final ParkingLotRepository parkingLotRepository;
    private final ParkingLotFinder parkingLotFinder;

    public ParkingApplicationService(ParkingLotRepository parkingLotRepository, ParkingLotFinder parkingLotFinder) {
        this.parkingLotRepository = parkingLotRepository;
        this.parkingLotFinder = parkingLotFinder;
    }

    public Ticket park(ParkCommand parkCommand) {
        final Car car = new Car(new CarNumber(parkCommand.getCarNumber()));

        final ParkingLot parkingLot = parkingLotFinder.find().orElseThrow(ParkingLotIsFullException::new);

        final Ticket ticket = parkingLot.park(car);
        parkingLotRepository.save(parkingLot);
        return ticket;
    }
}
