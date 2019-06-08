package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.TicketRepository;
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

        final ParkingRobotRepository parkingRobotRepository = new ParkingRobotRepository();
        parkingRobotRepository.save(new SimpleParkingRobot(parkingLotId1));
        parkingRobotRepository.save(new SmartParkingRobot(parkingLotId2));

        final ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);

        final TicketRepository ticketRepository = new TicketRepository();

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, parkingLotRepository, parkingRobotRepository);
        final CarNumber carNumber = new CarNumber("car-number-1");
        final Car car = new Car(carNumber);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber(carNumber.getValue());
        final Ticket ticket = parkingApplicationService.park(parkCommand);

        final PickApplicationService pickApplicationService = new PickApplicationService(ticketRepository, parkingLotRepository);
        final TicketId id = ticket.getId();

        final PickCommand pickCommand = new PickCommand();
        pickCommand.setTicketId(id.getValue());
        final Car pick = pickApplicationService.pick(pickCommand);

        assertThat(pick).isEqualTo(car);
    }
}
