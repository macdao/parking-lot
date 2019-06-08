package macdao.parkinglot;

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

    public void park(Car car) {
        if (isFull()) {
            throw new ParkingLotIsFullException();
        }
        carList.add(car);
    }

    public Car pick(CarNumber carNumber) {
        final Car result = carList.stream()
                .filter(car -> car.getCarNumber().equals(carNumber))
                .findFirst()
                .orElseThrow(CarNotFoundException::new);
        carList.remove(result);
        return result;
    }


    boolean hasSpace() {
        return !isFull();
    }

    private boolean isFull() {
        return carList.size() == capacity;
    }

    public int getSpace() {
        return capacity - carList.size();
    }

}
