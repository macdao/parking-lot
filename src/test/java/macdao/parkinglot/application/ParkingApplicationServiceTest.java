package macdao.parkinglot.application;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ParkingApplicationServiceTest {
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
    private CarNumber carNumber;

    @Before
    public void setUp() {
        parkingLotId1 = new ParkingLotId("parking-lot-id-1");
        carNumber = new CarNumber("car-number-1");

        when(parkingLot.park(any())).thenReturn(new Ticket(parkingLotId1, carNumber));
        when(parkingLotRepository.findById(parkingLotId1)).thenReturn(Optional.of(parkingLot));
        when(robot1.getManagedParkingLotIds()).thenReturn(singletonList(parkingLotId1));

        when(parkingRobotRepository.findAll()).thenReturn(asList(robot1, robot2));
    }

    @Test
    public void park_should_to_1st_robot() {
        when(robot1.find(parkingLot)).thenReturn(Optional.of(parkingLot));

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(parkingLotRepository, parkingRobotRepository);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber(carNumber.getValue());
        final Ticket ticket = parkingApplicationService.park(parkCommand);

        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId1);
        assertThat(ticket.getCarNumber()).isEqualTo(carNumber);
    }

    @Test
    public void park_should_to_2nd_robot_when_1st_cannot_park() {
        when(robot2.find()).thenReturn(Optional.of(parkingLot));

        final ParkingApplicationService parkingApplicationService = new ParkingApplicationService(parkingLotRepository, parkingRobotRepository);

        final ParkCommand parkCommand = new ParkCommand();
        parkCommand.setCarNumber(carNumber.getValue());
        final Ticket ticket = parkingApplicationService.park(parkCommand);

        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId1);
    }
}
