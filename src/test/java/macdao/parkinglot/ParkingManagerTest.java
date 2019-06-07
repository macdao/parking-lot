package macdao.parkinglot;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingManagerTest {
    @Test
    public void park_should_to_1st_robot() {
        final ParkingRobot robot1 = mock(ParkingRobot.class);
        final Ticket ticket1 = new Ticket(new ParkingLotId("parking-lot-id-1"));
        when(robot1.canPark()).thenReturn(true);
        when(robot1.park(any())).thenReturn(ticket1);

        final ParkingManager parkingManager = new ParkingManager(robot1, mock(ParkingRobot.class));

        final Ticket ticket = parkingManager.park(new Car());

        assertThat(ticket).isEqualTo(ticket1);
    }

    @Test
    public void park_should_to_2nd_robot_when_1st_cannot_park() {
        final ParkingRobot robot1 = mock(ParkingRobot.class);
        when(robot1.canPark()).thenReturn(false);

        final ParkingRobot robot2 = mock(ParkingRobot.class);
        final Ticket ticket2 = new Ticket(new ParkingLotId("parking-lot-id-2"));
        when(robot2.canPark()).thenReturn(true);
        when(robot2.park(any())).thenReturn(ticket2);

        final ParkingManager parkingManager = new ParkingManager(robot1, robot2);

        final Ticket ticket = parkingManager.park(new Car());

        assertThat(ticket).isEqualTo(ticket2);
    }
}
