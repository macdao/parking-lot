package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingApplicationServiceTest {
    @Mock
    private TicketRepository ticketRepository;
    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private ParkingLot parkingLot;
    @Mock
    private ParkingRobotRepository parkingRobotRepository;
    @Mock
    private ParkingRobot robot1;
    @Mock
    private ParkingRobot robot2;

    private ParkingLotId parkingLotId1;

    @Before
    public void setUp() {
        parkingLotId1 = new ParkingLotId("parking-lot-id-1");
        when(parkingLot.getId()).thenReturn(parkingLotId1);
        when(parkingLotRepository.findById(parkingLotId1)).thenReturn(Optional.of(parkingLot));
        when(robot1.getManagedParkingLotIds()).thenReturn(singletonList(parkingLotId1));

        when(parkingRobotRepository.findAll()).thenReturn(asList(robot1, robot2));
    }

    @Test
    public void park_should_to_1st_robot() {
        when(robot1.find(parkingLot)).thenReturn(Optional.of(parkingLot));
        final CarNumber carNumber = new CarNumber("car-number-1");

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, parkingLotRepository, parkingRobotRepository);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber(carNumber.getValue());
        final Ticket ticket = parkingApplicationService.park(parkCommand);

        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId1);
        assertThat(ticket.getCarNumber()).isEqualTo(carNumber);
    }

    @Test
    public void park_should_to_2nd_robot_when_1st_cannot_park() {
        when(robot2.find()).thenReturn(Optional.of(parkingLot));
        final ParkingLotId parkingLotId = new ParkingLotId("parking-lot-id-1");
        when(parkingLot.getId()).thenReturn(parkingLotId);
        final CarNumber carNumber = new CarNumber("car-number-1");

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, parkingLotRepository, parkingRobotRepository);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber(carNumber.getValue());
        final Ticket ticket = parkingApplicationService.park(parkCommand);

        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId);
    }

    @Test
    public void park_should_add_ticket_to_repo() {
        when(robot1.find(parkingLot)).thenReturn(Optional.of(parkingLot));
        final CarNumber carNumber = new CarNumber("car-number-1");

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, parkingLotRepository, parkingRobotRepository);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber(carNumber.getValue());
        final Ticket ticket = parkingApplicationService.park(parkCommand);

        verify(ticketRepository).save(ticket);
    }
}
