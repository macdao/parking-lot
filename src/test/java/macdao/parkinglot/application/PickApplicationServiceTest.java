package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.exception.TicketInvalidException;
import macdao.parkinglot.domain.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class PickApplicationServiceTest {
    @Mock
    private ParkingLotRepository parkingLotRepository;

    @Test
    public void pick_should_return_car() {
        final ParkingLotId parkingLotId = new ParkingLotId("parking-lot-id-1");
        final CarNumber carNumber = new CarNumber("car-number-1");
        final Ticket ticket = new Ticket(parkingLotId, carNumber);

        final Car carToBeParked = new Car(carNumber);

        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(parkingLot.pick(ticket.getId())).thenReturn(carToBeParked);
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        final PickApplicationService pickApplicationService = new PickApplicationService(parkingLotRepository);

        final PickCommand pickCommand = new PickCommand();
        pickCommand.setTicketId(ticket.getId().getValue());
        pickCommand.setParkingLotId("parking-lot-id-1");
        final Car car = pickApplicationService.pick(pickCommand);

        assertThat(car).isEqualTo(carToBeParked);
    }

    @Test(expected = TicketInvalidException.class)
    public void pick_should_fail_when_ticket_id_invalid() {
        final PickApplicationService pickApplicationService = new PickApplicationService(parkingLotRepository);

        final PickCommand pickCommand = new PickCommand();
        pickCommand.setTicketId("invalid-ticket-id");
        pickApplicationService.pick(pickCommand);
    }
}
