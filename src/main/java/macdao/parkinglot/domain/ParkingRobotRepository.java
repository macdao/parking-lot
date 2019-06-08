package macdao.parkinglot.domain;

import macdao.parkinglot.domain.model.ParkingRobot;

public interface ParkingRobotRepository {
    Iterable<ParkingRobot> findAll();

    void save(ParkingRobot parkingRobot);
}
