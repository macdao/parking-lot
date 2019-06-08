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
        final ParkingLotId parkingLotId1 = new ParkingLotId();
        final ParkingLot parkingLot1 = new ParkingLot(parkingLotId1, 10);
        final ParkingLotId parkingLotId2 = new ParkingLotId();
        final ParkingLot parkingLot2 = new ParkingLot(parkingLotId2, 10);

        final ParkingRobotRepository parkingRobotRepository = new ParkingRobotRepository();
        parkingRobotRepository.save(new SimpleParkingRobot(parkingLotId1));
        parkingRobotRepository.save(new SmartParkingRobot(parkingLotId2));

        final ParkingLotRepository parkingLotRepository = new ParkingLotRepository();
        parkingLotRepository.save(parkingLot1);
        parkingLotRepository.save(parkingLot2);

        final TicketRepository ticketRepository = new TicketRepository();

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, parkingLotRepository, parkingRobotRepository);
        final CarNumber carNumber = new CarNumber();
        final Car car = new Car(carNumber);

        final Ticket ticket = parkingApplicationService.park(car);

        final PickApplicationService pickApplicationService = new PickApplicationService(ticketRepository, parkingLot1, parkingLot2);
        final TicketId id = ticket.getId();
        final Car pick = pickApplicationService.pick(id);

        assertThat(pick).isEqualTo(car);
    }
}
