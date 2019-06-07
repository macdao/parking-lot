package macdao.parkinglot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PickManagerTest {
    @Test
    public void pick_should_return_car() {
        final ParkingLotId parkingLotId = new ParkingLotId("parking-lot-id");
        final Ticket ticket = new Ticket(parkingLotId);
        final Car carToBeParked = new Car();

        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.pick(ticket)).thenReturn(carToBeParked);
        when(parkingLot.getId()).thenReturn(parkingLotId);
        final PickManager pickManager = new PickManager(parkingLot);

        final Car car = pickManager.pick(ticket);

        assertThat(car).isEqualTo(carToBeParked);
    }
}
