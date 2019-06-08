package macdao.parkinglot.domain.model;

public class Car {
    private final CarNumber carNumber;

    public Car(CarNumber carNumber) {
        this.carNumber = carNumber;
    }

    public CarNumber getCarNumber() {
        return carNumber;
    }
}
