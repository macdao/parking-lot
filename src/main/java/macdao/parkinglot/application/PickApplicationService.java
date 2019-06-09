package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.exception.TicketInvalidException;
import macdao.parkinglot.domain.model.Car;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.ParkingLotId;
import macdao.parkinglot.domain.model.TicketId;
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
