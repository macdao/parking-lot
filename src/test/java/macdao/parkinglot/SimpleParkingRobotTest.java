package macdao.parkinglot;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleParkingRobotTest {

    private SimpleParkingRobot parkingRobot;

    @Before
    public void setUp() {
        final ParkingLot parkingLot1 = new ParkingLot(new ParkingLotId("parking-lot-id-1"), 1);
        final ParkingLot parkingLot2 = new ParkingLot(new ParkingLotId("parking-lot-id-2"), 1);
        parkingRobot = new SimpleParkingRobot(parkingLot1, parkingLot2);
    }

    @Test
    public void park_should_to_1st_parking_lot_when_1st_has_space() {
        final Ticket ticket = parkingRobot.park(new Car());

        assertThat(ticket.getParkingLotId().toString()).isEqualTo("parking-lot-id-1");
    }

    @Test
    public void park_should_to_2nd_parking_lot_when_1st_has_no_space() {
        parkingRobot.park(new Car());

        final Ticket ticket = parkingRobot.park(new Car());

        assertThat(ticket.getParkingLotId().toString()).isEqualTo("parking-lot-id-2");
    }

    @Test(expected = ParkingLotIsFullException.class)
    public void park_should_fail_when_no_space() {
        parkingRobot.park(new Car());
        parkingRobot.park(new Car());
        parkingRobot.park(new Car());
    }

    @Test
    public void can_park_should_return_true_when_parking_lot_have_space() {
        final ParkingLot parkingLot1 = new ParkingLot(new ParkingLotId("parking-lot-id-1"), 1);

        parkingRobot = new SimpleParkingRobot(parkingLot1);

        assertThat(parkingRobot.canPark()).isTrue();
    }

    @Test
    public void can_park_should_return_true_when_parking_lot_have__no_space() {
        final ParkingLot parkingLot1 = new ParkingLot(new ParkingLotId("parking-lot-id-1"), 0);

        parkingRobot = new SimpleParkingRobot(parkingLot1);

        assertThat(parkingRobot.canPark()).isFalse();
    }
}
