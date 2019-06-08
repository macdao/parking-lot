package macdao.parkinglot.domain;

import macdao.parkinglot.domain.model.ParkingRobot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParkingRobotRepository {
    private final List<ParkingRobot> robotList = new ArrayList<>();

    public Iterable<ParkingRobot> findAll() {
        return Collections.unmodifiableCollection(robotList);
    }

    public void save(ParkingRobot parkingRobot) {
        robotList.add(parkingRobot);
    }

}
