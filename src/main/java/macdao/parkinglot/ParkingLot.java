package macdao.parkinglot;

import java.util.ArrayList;
import java.util.List;

public class ParkingLot {
    private final ParkingLotId id;
    private final int capacity;
    private List<ParkingItem> items = new ArrayList<>();

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
        final Ticket ticket = new Ticket(id);
        items.add(new ParkingItem(car, ticket));
        return ticket;
    }

    public Car pick(Ticket ticket) {
        return items.stream()
                .filter(item -> item.ticket.equals(ticket))
                .findFirst()
                .map(item -> {
                    items.remove(item);
                    return item;
                })
                .map((item) -> item.car)
                .orElseThrow(TicketInvalidException::new);
    }

    boolean hasSpace() {
        return !isFull();
    }

    private boolean isFull() {
        return items.size() == capacity;
    }

    public int getSpace() {
        return capacity - items.size();
    }

    private static class ParkingItem {
        private final Car car;
        private final Ticket ticket;

        private ParkingItem(Car car, Ticket ticket) {
            this.car = car;
            this.ticket = ticket;
        }
    }
}
