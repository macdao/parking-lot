package macdao.parkinglot.domain.model.parkingrobot;

public interface ParkingRobotRepository {
    Iterable<ParkingRobot> findAll();

    void save(ParkingRobot parkingRobot);
}
