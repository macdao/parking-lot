package macdao.parkinglot.domain.model;

import lombok.Data;

import java.util.UUID;

@Data
public class TicketId {
    private final String value;

    public TicketId() {
        this.value = UUID.randomUUID().toString();
    }

    public TicketId(String value) {
        this.value = value;
    }
}
