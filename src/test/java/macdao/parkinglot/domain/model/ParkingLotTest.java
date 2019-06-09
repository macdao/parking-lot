package macdao.parkinglot.domain.model;

import macdao.parkinglot.domain.exception.CarNotFoundException;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;
import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingLotTest {

    private ParkingLot parkingLot;
    private ParkingLotId parkingLotId;
    private CarNumber carNumber1;
    private Car car1;

    @Before
    public void setUp() {
        parkingLotId = new ParkingLotId("parking-lot-id-1");
        parkingLot = new ParkingLot(parkingLotId, 10);
        carNumber1 = new CarNumber("car-number-1");
        car1 = new Car(carNumber1);
    }

    @Test
    public void can_park() {
        Ticket ticket = parkingLot.park(new Car(carNumber1));

        assertThat(ticket.getId()).isNotNull();
        assertThat(ticket.getParkingLotId()).isEqualTo(parkingLotId);
        assertThat(ticket.getCarNumber()).isEqualTo(carNumber1);
    }

    @Test
    public void can_pick_car() {
        parkingLot.park(car1);

        assertThat(parkingLot.pick(car1.getCarNumber())).isEqualTo(car1);
    }

    @Test
    public void can_pick_correct_car_when_2_cars_parked() {
        parkingLot.park(car1);

        final Car car2 = new Car(new CarNumber("car-number-2"));
        parkingLot.park(car2);

        assertThat(parkingLot.pick(car2.getCarNumber())).isEqualTo(car2);
        assertThat(parkingLot.pick(car1.getCarNumber())).isEqualTo(car1);
    }

    @Test(expected = ParkingLotIsFullException.class)
    public void cannot_park_2_cars_when_parking_lot_capacity_is_1() {
        final ParkingLot parkingLot = new ParkingLot(parkingLotId, 1);
        parkingLot.park(car1);
        parkingLot.park(new Car(new CarNumber("car-number-2")));
    }

    @Test(expected = CarNotFoundException.class)
    public void pick_should_fail_when_ticket_is_invalid() {
        parkingLot.pick(carNumber1);
    }

    @Test
    public void get_space_should_be_8_when_capacity_is_10_and_park_2() {
        parkingLot.park(car1);
        parkingLot.park(new Car(new CarNumber("car-number-2")));
        assertThat(parkingLot.getSpace()).isEqualTo(8);
    }

    @Test(expected = CarNotFoundException.class)
    public void pick_should_fail_when_use_ticket_twice() {
        parkingLot.park(car1);
        parkingLot.pick(car1.getCarNumber());

        parkingLot.pick(car1.getCarNumber());
    }
}
