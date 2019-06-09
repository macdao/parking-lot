package macdao.parkinglot.application;

import lombok.Data;

@Data
public class PickCommand {
    private String ticketId;
    private String parkingLotId;
}
