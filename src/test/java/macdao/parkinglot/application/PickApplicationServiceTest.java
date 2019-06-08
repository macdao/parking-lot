package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.exception.TicketInvalidException;
import macdao.parkinglot.domain.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)

public class PickApplicationServiceTest {
    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private TicketRepository ticketRepository;

    @Test
    public void pick_should_return_car() {
        final ParkingLotId parkingLotId = new ParkingLotId();
        final CarNumber carNumber = new CarNumber("car-number-1");
        final Ticket ticket = new Ticket(parkingLotId, carNumber);

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        final Car carToBeParked = new Car(carNumber);

        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.pick(carNumber)).thenReturn(carToBeParked);
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        final PickApplicationService pickApplicationService = new PickApplicationService(ticketRepository, parkingLotRepository);

        final Car car = pickApplicationService.pick(ticket.getId());

        assertThat(car).isEqualTo(carToBeParked);
    }

    @Test(expected = TicketInvalidException.class)
    public void pick_should_fail_when_ticket_id_invalid() {
        final PickApplicationService pickApplicationService = new PickApplicationService(ticketRepository, parkingLotRepository);

        pickApplicationService.pick(new TicketId());
    }

    @Test
    public void pick_should_remove_ticket_from_repo() {
        final ParkingLotId parkingLotId = new ParkingLotId();
        final CarNumber carNumber = new CarNumber("car-number-1");
        final Ticket ticket = new Ticket(parkingLotId, carNumber);

        when(ticketRepository.findById(ticket.getId())).thenReturn(Optional.of(ticket));

        final Car carToBeParked = new Car(carNumber);

        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.pick(carNumber)).thenReturn(carToBeParked);
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        final PickApplicationService pickApplicationService = new PickApplicationService(ticketRepository, parkingLotRepository);

        pickApplicationService.pick(ticket.getId());

        verify(ticketRepository).delete(ticket);
    }
}
