package macdao.parkinglot.domain.model;

import macdao.parkinglot.domain.exception.CarNotFoundException;
import macdao.parkinglot.domain.exception.ParkingLotIsFullException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParkingLot {
    private final ParkingLotId id;
    private final int capacity;
    private List<Car> carList = new ArrayList<>();
    private Map<TicketId, Car> tickets = new HashMap<>();

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
        final Ticket ticket = new Ticket(this.id, car.getCarNumber());
        tickets.put(ticket.getId(), car);
        return ticket;
    }

    public Car pick(TicketId ticketId) {
        final Car car = tickets.get(ticketId);
        if (car == null) {
            throw new CarNotFoundException();
        }
        tickets.remove(ticketId);
        return car;
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
