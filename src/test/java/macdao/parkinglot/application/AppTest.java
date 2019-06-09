package macdao.parkinglot.application;

import macdao.parkinglot.adapter.mem.MemParkingLotRepository;
import macdao.parkinglot.adapter.mem.MemParkingRobotRepository;
import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.model.*;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
    @Test
    public void park() {
        final ParkingLotId parkingLotId1 = new ParkingLotId("parking-lot-id-1");
        final ParkingLot parkingLot1 = new ParkingLot(parkingLotId1, 10);
        final ParkingLotId parkingLotId2 = new ParkingLotId("parking-lot-id-2");
        final ParkingLot parkingLot2 = new ParkingLot(parkingLotId2, 10);

        final ParkingRobotRepository parkingRobotRepository = new MemParkingRobotRepository();
        parkingRobotRepository.save(new ParkingRobot(new SimpleParkingPolicy(), parkingLotId1));
        parkingRobotRepository.save(new ParkingRobot(new SmartParkingPolicy(), parkingLotId2));

        final ParkingLotRepository parkingLotRepository = new MemParkingLotRepository();
        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(parkingLotRepository, parkingRobotRepository);
        final CarNumber carNumber = new CarNumber("car-number-1");
        final Car car = new Car(carNumber);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber(carNumber.getValue());
        final Ticket ticket = parkingApplicationService.park(parkCommand);

        final PickApplicationService pickApplicationService = new PickApplicationService(parkingLotRepository);

        final PickCommand pickCommand = new PickCommand();
        pickCommand.setTicketId(ticket.getId().getValue());
        pickCommand.setParkingLotId(ticket.getParkingLotId().getValue());
        final Car pick = pickApplicationService.pick(pickCommand);

        assertThat(pick).isEqualTo(car);
    }
}
