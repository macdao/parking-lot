package macdao.parkinglot;

import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ParkingManagerTest {
    @Test
    public void park_should_to_1st_robot() {
        final ParkingRobot robot = mock(ParkingRobot.class);
        final ParkingLot parkingLot = mock(ParkingLot.class);
        when(robot.find()).thenReturn(Optional.of(parkingLot));
        final ParkingLotId parkingLotId = new ParkingLotId("parking-lot-id-1");
        when(parkingLot.getId()).thenReturn(parkingLotId);
        final CarNumber carNumber = new CarNumber("car-number-1");
        final Car car = new Car(carNumber);

        final ParkingManager parkingManager = new ParkingManager(robot, mock(ParkingRobot.class));

        final Ticket ticket = parkingManager.park(car);

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
        final ParkingLotId parkingLotId = new ParkingLotId("parking-lot-id-1");
        when(parkingLot.getId()).thenReturn(parkingLotId);
        final Car car = new Car(new CarNumber("car-number-1"));

        final ParkingManager parkingManager = new ParkingManager(robot1, robot2);

        final Ticket ticket = parkingManager.park(car);

        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId);
    }
}
