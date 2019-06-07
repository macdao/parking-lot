package macdao.parkinglot;

public class ParkingLotId {
    private final String value;

    public ParkingLotId(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
