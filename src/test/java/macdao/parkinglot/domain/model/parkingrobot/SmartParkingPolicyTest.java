package macdao.parkinglot.domain.model.parkingrobot;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class SmartParkingPolicyTest {
    private SmartParkingPolicy parkingPolicy;
    private ParkingLot parkingLot1;
    private ParkingLot parkingLot2;

    @Before
    public void setUp() {
        parkingLot1 = mock(ParkingLot.class);
        when(parkingLot1.hasSpace()).thenReturn(true);
        parkingLot2 = mock(ParkingLot.class);
        when(parkingLot2.hasSpace()).thenReturn(true);
        parkingPolicy = new SmartParkingPolicy();
    }

    @Test
    public void find_should_1st_parking_lot_when_1st_has_more_space() {
        when(parkingLot1.getSpace()).thenReturn(2);
        when(parkingLot2.getSpace()).thenReturn(1);

        final Optional<ParkingLot> parkingLot = parkingPolicy.find(parkingLot1, parkingLot2);

        Assertions.assertThat(parkingLot).containsSame(parkingLot1);
    }

    @Test
    public void find_should_2nd_parking_lot_when_2nd_has_more_space() {
        when(parkingLot1.getSpace()).thenReturn(1);
        when(parkingLot2.getSpace()).thenReturn(2);

        final Optional<ParkingLot> parkingLot = parkingPolicy.find(parkingLot1, parkingLot2);

        Assertions.assertThat(parkingLot).containsSame(parkingLot2);
    }

    @Test
    public void find_should_1st_parking_lot_when_all_have_same_space() {
        when(parkingLot1.getSpace()).thenReturn(1);
        when(parkingLot2.getSpace()).thenReturn(1);

        final Optional<ParkingLot> parkingLot = parkingPolicy.find(parkingLot1, parkingLot2);

        Assertions.assertThat(parkingLot).containsSame(parkingLot1);
    }

    @Test
    public void find_should_return_empty_when_no_space() {
        when(parkingLot1.getSpace()).thenReturn(0);
        when(parkingLot1.hasSpace()).thenReturn(false);
        when(parkingLot2.getSpace()).thenReturn(0);
        when(parkingLot2.hasSpace()).thenReturn(false);

        final Optional<ParkingLot> parkingLot = parkingPolicy.find(parkingLot1, parkingLot2);

        Assertions.assertThat(parkingLot).isEmpty();
    }
}
