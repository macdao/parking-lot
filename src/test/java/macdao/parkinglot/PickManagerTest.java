package macdao.parkinglot;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class PickManagerTest {
    @Test
    public void pick_should_return_car() {
        final ParkingLotId parkingLotId = new ParkingLotId();
        final CarNumber carNumber = new CarNumber();
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

    @Test(expected = TicketInvalidException.class)
    public void pick_should_fail_when_ticket_id_invalid() {
        final ParkingLot parkingLot = mock(ParkingLot.class);
        final TicketRepository ticketRepository = mock(TicketRepository.class);
        final PickManager pickManager = new PickManager(ticketRepository, parkingLot);

        pickManager.pick(new TicketId());
    }

    @Test
    public void pick_should_remove_ticket_from_repo() {
        final ParkingLot parkingLot = mock(ParkingLot.class);
        final TicketRepository ticketRepository = mock(TicketRepository.class);
        final TicketId ticketId = new TicketId();
        final ParkingLotId parkingLotId = mock(ParkingLotId.class);
        final CarNumber carNumber = mock(CarNumber.class);
        final Ticket ticket = new Ticket(parkingLotId, carNumber);

        when(ticketRepository.findById(ticketId)).thenReturn(Optional.of(ticket));
        when(parkingLot.getId()).thenReturn(parkingLotId);
        when(parkingLot.pick(carNumber)).thenReturn(mock(Car.class));

        final PickManager pickManager = new PickManager(ticketRepository, parkingLot);

        pickManager.pick(ticketId);

        verify(ticketRepository).delete(ticket);
    }
}
