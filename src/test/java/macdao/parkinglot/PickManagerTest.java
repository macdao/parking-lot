package macdao.parkinglot;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PickManagerTest {
    @Test
    public void pick_should_return_car() {
        final ParkingLotId parkingLotId = new ParkingLotId("parking-lot-id");
        final CarNumber carNumber = new CarNumber("car-number-1");
        final Ticket ticket = new Ticket(parkingLotId, carNumber);

        final TicketRepository ticketRepository = mock(TicketRepository.class);
        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        final Car carToBeParked = new Car(carNumber);

        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.getId()).thenReturn(parkingLotId);
        when(parkingLot.pick(carNumber)).thenReturn(carToBeParked);
        final PickManager pickManager = new PickManager(ticketRepository, parkingLot);

        final Car car = pickManager.pick(ticket.getId());

        assertThat(car).isEqualTo(carToBeParked);
    }
}
