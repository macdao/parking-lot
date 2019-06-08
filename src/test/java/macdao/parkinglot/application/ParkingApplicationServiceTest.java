package macdao.parkinglot.application;

import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.model.*;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

public class ParkingApplicationServiceTest {
    private TicketRepository ticketRepository;

    @Before
    public void setUp() {
        ticketRepository = mock(TicketRepository.class);
    }

    @Test
    public void park_should_to_1st_robot() {
        final ParkingRobot robot = mock(ParkingRobot.class);
        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(robot.find()).thenReturn(Optional.of(parkingLot));
        final ParkingLotId parkingLotId = new ParkingLotId();
        when(parkingLot.getId()).thenReturn(parkingLotId);
        final CarNumber carNumber = new CarNumber();
        final Car car = new Car(carNumber);

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, robot, mock(ParkingRobot.class));

        final Ticket ticket = parkingApplicationService.park(car);

        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId);
        assertThat(ticket.getCarNumber()).isEqualTo(carNumber);
    }

    @Test
    public void park_should_to_2nd_robot_when_1st_cannot_park() {
        final ParkingRobot robot1 = mock(ParkingRobot.class);
        when(robot1.find()).thenReturn(Optional.empty());

        final ParkingRobot robot2 = mock(ParkingRobot.class);
        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(robot2.find()).thenReturn(Optional.of(parkingLot));
        final ParkingLotId parkingLotId = new ParkingLotId();
        when(parkingLot.getId()).thenReturn(parkingLotId);
        final Car car = new Car(new CarNumber());

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, robot1, robot2);

        final Ticket ticket = parkingApplicationService.park(car);

        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId);
    }

    @Test
    public void park_should_add_ticket_to_repo() {
        final ParkingRobot robot = mock(ParkingRobot.class);
        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(robot.find()).thenReturn(Optional.of(parkingLot));
        final ParkingLotId parkingLotId = new ParkingLotId();
        when(parkingLot.getId()).thenReturn(parkingLotId);
        final CarNumber carNumber = new CarNumber();
        final Car car = new Car(carNumber);


        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(ticketRepository, robot, mock(ParkingRobot.class));

        final Ticket ticket = parkingApplicationService.park(car);

        verify(ticketRepository).save(ticket);
    }
}
