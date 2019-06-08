package macdao.parkinglot;

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
        final ParkingManager parkingManager = new ParkingManager(ticketRepository, simpleParkingRobot, smartParkingRobot);
        final CarNumber carNumber = new CarNumber();
        final Car car = new Car(carNumber);

        final Ticket ticket = parkingManager.park(car);

        final PickManager pickManager = new PickManager(ticketRepository, parkingLot1, parkingLot2);
        final TicketId id = ticket.getId();
        final Car pick = pickManager.pick(id);

        assertThat(pick).isEqualTo(car);
    }
}
