package macdao.parkinglot;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class SmartSimpleParkingRobotTest {

    private ParkingLot parkingLotWith1Spaces;
    private ParkingLot parkingLotWith2Spaces;

    @Before
    public void setUp() {
        parkingLotWith1Spaces = mockParkingLot("parking-lot-id-1", 1);
        parkingLotWith2Spaces = mockParkingLot("parking-lot-id-2", 2);
    }

    private ParkingLot mockParkingLot(String value, int value1) {
        final ParkingLotId parkingLotId = new ParkingLotId(value);
        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getSpace()).thenReturn(value1);
        when(parkingLot.getId()).thenReturn(parkingLotId);
        when(parkingLot.park(any())).thenReturn(new Ticket(parkingLotId));
        when(parkingLot.pick(any())).thenReturn(new Car());
        return parkingLot;
    }

    @Test
    public void park_should_1st_parking_lot_when_1st_has_more_space() {
        final SmartParkingRobot parkingRobot = new SmartParkingRobot(parkingLotWith2Spaces, parkingLotWith1Spaces);

        final Ticket ticket = parkingRobot.park(new Car());

        assertThat(ticket.getParkingLotId().toString()).isEqualTo("parking-lot-id-2");
    }

    @Test
    public void park_should_2nd_parking_lot_when_2nd_has_more_space() {
        final SmartParkingRobot smartParkingRobot = new SmartParkingRobot(parkingLotWith1Spaces, parkingLotWith2Spaces);

        final Ticket ticket = smartParkingRobot.park(new Car());

        assertThat(ticket.getParkingLotId().toString()).isEqualTo("parking-lot-id-2");
    }

    @Test
    public void park_should_1st_parking_lot_when_all_have_same_space() {
        final SmartParkingRobot smartParkingRobot = new SmartParkingRobot(
                parkingLotWith1Spaces,
                new ParkingLot(new ParkingLotId("id"), 1)
        );

        final Ticket ticket = smartParkingRobot.park(new Car());

        assertThat(ticket.getParkingLotId().toString()).isEqualTo("parking-lot-id-1");
    }

    @Test
    public void can_park_should_return_true_when_parking_lot_have_space() {
        final ParkingLot parkingLot1 = new ParkingLot(new ParkingLotId("parking-lot-id-1"), 1);

        SmartParkingRobot parkingRobot = new SmartParkingRobot(parkingLot1);

        assertThat(parkingRobot.canPark()).isTrue();
    }
}
