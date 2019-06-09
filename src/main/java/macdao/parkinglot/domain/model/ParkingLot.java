package macdao.parkinglot.domain.model;

import macdao.parkinglot.domain.exception.CarNotFoundException;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final ParkingLotId id;
    private final int capacity;
    private List<Car> carList = new ArrayList<>();

    public ParkingLot(ParkingLotId id, int capacity) {
        this.id = id;
        this.capacity = capacity;
    }

    public ParkingLotId getId() {
        return id;
    }

    public Ticket park(Car car) {
        if (isFull()) {
            throw new ParkingLotIsFullException();
        }
        carList.add(car);
        return new Ticket(this.id, car.getCarNumber());
    }

    public Car pick(CarNumber carNumber) {
        final Car result = carList.stream()
                .filter(car -> car.getCarNumber().equals(carNumber))
                .findFirst()
                .orElseThrow(CarNotFoundException::new);
        carList.remove(result);
        return result;
    }


    public boolean hasSpace() {
        return !isFull();
    }

    public int getSpace() {
        return capacity - carList.size();
    }

    private boolean isFull() {
        return carList.size() == capacity;
    }

}
