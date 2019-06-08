package macdao.parkinglot.application;

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
        final TicketRepository ticketRepository = new TicketRepository();
        final SimpleParkingRobot simpleParkingRobot = new SimpleParkingRobot(parkingLot1);
        final SmartParkingRobot smartParkingRobot = new SmartParkingRobot(parkingLot2);
        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, simpleParkingRobot, smartParkingRobot);
        final CarNumber carNumber = new CarNumber();
        final Car car = new Car(carNumber);

        final Ticket ticket = parkingApplicationService.park(car);

        final PickApplicationService pickApplicationService = new PickApplicationService(ticketRepository, parkingLot1, parkingLot2);
        final TicketId id = ticket.getId();
        final Car pick = pickApplicationService.pick(id);

        assertThat(pick).isEqualTo(car);
    }
}
