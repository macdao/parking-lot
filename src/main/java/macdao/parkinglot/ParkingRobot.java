package macdao.parkinglot;

public interface ParkingRobot {
    Ticket park(Car car);

    boolean canPark();
}
