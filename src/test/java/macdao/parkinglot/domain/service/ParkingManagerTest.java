package macdao.parkinglot.domain.service;

import macdao.parkinglot.domain.model.parkinglot.ParkingLot;
import macdao.parkinglot.domain.model.parkinglot.ParkingLotId;
import macdao.parkinglot.domain.model.parkinglot.ParkingLotRepository;
import macdao.parkinglot.domain.model.parkingrobot.ParkingRobot;
import macdao.parkinglot.domain.model.parkingrobot.ParkingRobotRepository;
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

public class ParkingManagerTest {
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
        final ParkingManager parkingManager = new ParkingManager(parkingLotRepository, parkingRobotRepository);

        final Optional<ParkingLot> result = parkingManager.find();

        assertThat(result).containsSame(parkingLot);
    }

    @Test
    public void find_should_return_2nd_robot_when_1st_robot_not_returned() {
        when(parkingRobotRepository.findAll()).thenReturn(Arrays.asList(robot1, robot2));
        when(robot1.find(any())).thenReturn(Optional.empty());

        when(robot2.getManagedParkingLotIds()).thenReturn(singletonList(parkingLotId));
        when(parkingLotRepository.findById(parkingLotId)).thenReturn(Optional.of(parkingLot));
        when(robot2.find(parkingLot)).thenReturn(Optional.of(parkingLot));

        final ParkingManager parkingManager = new ParkingManager(parkingLotRepository, parkingRobotRepository);

        final Optional<ParkingLot> result = parkingManager.find();

        assertThat(result).containsSame(parkingLot);
    }
}