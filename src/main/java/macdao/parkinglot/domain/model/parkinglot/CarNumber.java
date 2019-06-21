package macdao.parkinglot.domain.model.parkinglot;

import lombok.Data;

@Data
public class CarNumber {
    private final String value;

    public CarNumber(String value) {
        this.value = value;
    }
}
