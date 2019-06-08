package macdao.parkinglot;

import java.util.UUID;

public class TicketId {
    private final String id;

    public TicketId() {
        this.id = UUID.randomUUID().toString();
    }

}
