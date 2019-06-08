package macdao.parkinglot;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ParkingLotTest {

    private ParkingLot parkingLot;

    @Before
    public void setUp() {
        parkingLot = new ParkingLot(new ParkingLotId("parking-lot-id-1"), 10);
    }

    @Test
    public void can_park() {
        final Ticket ticket = parkingLot.park(new Car(new CarNumber("car-number-1")));

        assertThat(ticket).isNotNull();
        assertThat(ticket.getParkingLotId().toString()).isEqualTo("parking-lot-id-1");
    }

    @Test
    public void can_pick_car() {
        final Car car = new Car(new CarNumber("car-number-1"));
        final Ticket ticket = parkingLot.park(car);

        assertThat(parkingLot.pick(ticket)).isEqualTo(car);
    }

    @Test
    public void can_pick_correct_car_when_2_cars_parked() {
        final Car car1 = new Car(new CarNumber("car-number-1"));
        final Ticket ticket1 = parkingLot.park(car1);

        final Car car2 = new Car(new CarNumber("car-number-1"));
        final Ticket ticket2 = parkingLot.park(car2);

        assertThat(parkingLot.pick(ticket2)).isEqualTo(car2);
        assertThat(parkingLot.pick(ticket1)).isEqualTo(car1);
    }

    @Test(expected = ParkingLotIsFullException.class)
    public void cannot_park_2_cars_when_parking_lot_capacity_is_1() {
        final ParkingLot parkingLot = new ParkingLot(new ParkingLotId("parking-lot-id-1"), 1);
        parkingLot.park(new Car(new CarNumber("car-number-1")));
        parkingLot.park(new Car(new CarNumber("car-number-1")));
    }

    @Test(expected = TicketInvalidException.class)
    public void pick_should_fail_when_ticket_is_invalid() {
        parkingLot.pick(new Ticket(new ParkingLotId("parking-lot-id-1")));
    }

    @Test
    public void get_space_should_be_8_when_capacity_is_10_and_park_2() {
        parkingLot.park(new Car(new CarNumber("car-number-1")));
        parkingLot.park(new Car(new CarNumber("car-number-1")));
        assertThat(parkingLot.getSpace()).isEqualTo(8);
    }

    @Test(expected = TicketInvalidException.class)
    public void pick_should_fail_when_use_ticket_twice() {
        final Car car1 = new Car(new CarNumber("car-number-1"));
        final Ticket ticket1 = parkingLot.park(car1);
        parkingLot.pick(ticket1);

        parkingLot.pick(ticket1);
    }
}
