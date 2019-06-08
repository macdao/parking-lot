package macdao.parkinglot.domain.model;

import lombok.Data;

@Data
public class ParkingLotId {
    private final String value;

    public ParkingLotId(String value) {
        this.value = value;
    }
}
