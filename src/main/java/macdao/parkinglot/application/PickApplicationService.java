package macdao.parkinglot.application;

import macdao.parkinglot.domain.model.parkinglot.ParkingLotRepository;
import macdao.parkinglot.domain.exception.TicketInvalidException;
import macdao.parkinglot.domain.model.parkinglot.Car;
import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.ParkingLotId;
import macdao.parkinglot.domain.model.parkinglot.TicketId;
import org.springframework.stereotype.Service;

@Service
public class PickApplicationService {
    private final ParkingLotRepository parkingLotRepository;

    public PickApplicationService(ParkingLotRepository parkingLotRepository) {
        this.parkingLotRepository = parkingLotRepository;
    }

    public Car pick(PickCommand pickCommand) {
        final ParkingLot parkingLot = parkingLotRepository.findById(new ParkingLotId(pickCommand.getParkingLotId()))
                .orElseThrow(TicketInvalidException::new);

        final Car car = parkingLot.pick(new TicketId(pickCommand.getTicketId()));

        parkingLotRepository.save(parkingLot);

        return car;
    }
}
