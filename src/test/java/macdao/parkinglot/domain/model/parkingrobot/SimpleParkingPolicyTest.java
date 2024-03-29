package macdao.parkinglot.domain.model.parkingrobot;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SimpleParkingPolicyTest {
    private SimpleParkingPolicy parkingRobot;
    private ParkingLot parkingLot1;
    private ParkingLot parkingLot2;

    @Before
    public void setUp() {
        parkingLot1 = mock(ParkingLot.class);
        parkingLot2 = mock(ParkingLot.class);
        parkingRobot = new SimpleParkingPolicy();
    }

    @Test
    public void find_should_return_1st_parking_lot_when_1st_has_space() {
        when(parkingLot1.hasSpace()).thenReturn(true);

        final Optional<ParkingLot> parkingLot = parkingRobot.find(parkingLot1, parkingLot2);

        assertThat(parkingLot).containsSame(parkingLot1);
    }

    @Test
    public void find_should_return_2nd_parking_lot_when_1st_has_no_space() {
        when(parkingLot1.hasSpace()).thenReturn(false);
        when(parkingLot2.hasSpace()).thenReturn(true);

        final Optional<ParkingLot> parkingLot = parkingRobot.find(parkingLot1, parkingLot2);

        assertThat(parkingLot).containsSame(parkingLot2);
    }

    @Test
    public void find_should_return_empty_when_no_space() {
        when(parkingLot1.hasSpace()).thenReturn(false);
        when(parkingLot2.hasSpace()).thenReturn(false);

        final Optional<ParkingLot> parkingLot = parkingRobot.find(parkingLot1, parkingLot2);

        assertThat(parkingLot).isEmpty();
    }
}
