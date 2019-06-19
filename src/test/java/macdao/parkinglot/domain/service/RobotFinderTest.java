package macdao.parkinglot.domain.service;

import macdao.parkinglot.domain.ParkingLotRepository;
import macdao.parkinglot.domain.ParkingRobotRepository;
import macdao.parkinglot.domain.model.ParkingLot;
import macdao.parkinglot.domain.model.ParkingLotId;
import macdao.parkinglot.domain.model.ParkingRobot;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)

public class RobotFinderTest {
    @Mock
    private ParkingLotRepository parkingLotRepository;
    @Mock
    private ParkingRobotRepository parkingRobotRepository;
    @Mock
    private ParkingLot parkingLot;
    @Mock
    private ParkingRobot robot1;
    @Mock
    private ParkingRobot robot2;
    private ParkingLotId parkingLotId;

    @Before
    public void setUp() {
        parkingLotId = new ParkingLotId("parking-lot-id-1");
    }

    @Test
    public void find_should_return_1st_robot_when_1st_robot_returned() {
        when(parkingRobotRepository.findAll()).thenReturn(singletonList(robot1));
        when(robot1.getManagedParkingLotIds()).thenReturn(singletonList(parkingLotId));
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        when(robot1.find(parkingLot)).thenReturn(Optional.of(parkingLot));
        final RobotFinder robotFinder = new RobotFinder(parkingLotRepository, parkingRobotRepository);

        final Optional<ParkingLot> result = robotFinder.find();

        assertThat(result).containsSame(parkingLot);
    }

    @Test
    public void find_should_return_2nd_robot_when_1st_robot_not_returned() {
        when(parkingRobotRepository.findAll()).thenReturn(Arrays.asList(robot1, robot2));
        when(robot1.find(any())).thenReturn(Optional.empty());

        when(robot2.getManagedParkingLotIds()).thenReturn(singletonList(parkingLotId));
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        when(robot2.find(parkingLot)).thenReturn(Optional.of(parkingLot));

        final RobotFinder robotFinder = new RobotFinder(parkingLotRepository, parkingRobotRepository);

        final Optional<ParkingLot> result = robotFinder.find();

        assertThat(result).containsSame(parkingLot);
    }
}