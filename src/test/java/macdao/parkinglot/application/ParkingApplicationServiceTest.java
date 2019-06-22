package macdao.parkinglot.application;

import macdao.parkinglot.domain.model.parkinglot.ParkingLotRepository;
import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.Ticket;
import macdao.parkinglot.domain.service.ParkingManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingApplicationServiceTest {
    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private ParkingLot parkingLot;
    @Mock
    private ParkingManager parkingManager;

    @Test
    public void park_should_to_found_robot() {
        when(parkingManager.find()).thenReturn(Optional.of(parkingLot));
        final Ticket ticket = mock(Ticket.class);
        when(parkingLot.park(any())).thenReturn(ticket);

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(parkingLotRepository, parkingManager);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber("car-number-1");
        final Ticket returnTicket = parkingApplicationService.park(parkCommand);

        assertThat(returnTicket).isEqualTo(ticket);
    }
}
