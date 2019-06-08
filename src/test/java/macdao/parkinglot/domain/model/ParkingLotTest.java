package macdao.parkinglot.domain.model;

import macdao.parkinglot.domain.exception.CarNotFoundException;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingLotTest {

    private ParkingLot parkingLot;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(new ParkingLotId(), 10);
    }

    @Test
    public void can_park() {
        parkingLot.park(new Car(new CarNumber()));
    }

    @Test
    public void can_pick_car() {
        final Car car = new Car(new CarNumber());
        parkingLot.park(car);

        assertThat(parkingLot.pick(car.getCarNumber())).isEqualTo(car);
    }

    @Test
    public void can_pick_correct_car_when_2_cars_parked() {
        final Car car1 = new Car(new CarNumber());
        parkingLot.park(car1);

        final Car car2 = new Car(new CarNumber());
        parkingLot.park(car2);

        assertThat(parkingLot.pick(car2.getCarNumber())).isEqualTo(car2);
        assertThat(parkingLot.pick(car1.getCarNumber())).isEqualTo(car1);
    }

    @Test(expected = ParkingLotIsFullException.class)
    public void cannot_park_2_cars_when_parking_lot_capacity_is_1() {
        final ParkingLot parkingLot = new ParkingLot(new ParkingLotId(), 1);
        parkingLot.park(new Car(new CarNumber()));
        parkingLot.park(new Car(new CarNumber()));
    }

    @Test(expected = CarNotFoundException.class)
    public void pick_should_fail_when_ticket_is_invalid() {
        parkingLot.pick(new CarNumber());
    }

    @Test
    public void get_space_should_be_8_when_capacity_is_10_and_park_2() {
        parkingLot.park(new Car(new CarNumber()));
        parkingLot.park(new Car(new CarNumber()));
        assertThat(parkingLot.getSpace()).isEqualTo(8);
    }

    @Test(expected = CarNotFoundException.class)
    public void pick_should_fail_when_use_ticket_twice() {
        final Car car1 = new Car(new CarNumber());
        parkingLot.park(car1);
        parkingLot.pick(car1.getCarNumber());

        parkingLot.pick(car1.getCarNumber());
    }
}
