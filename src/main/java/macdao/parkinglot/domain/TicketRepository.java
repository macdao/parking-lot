package macdao.parkinglot.domain;

import macdao.parkinglot.domain.model.Ticket;
import macdao.parkinglot.domain.model.TicketId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TicketRepository {
    private List<Ticket> ticketList = new ArrayList<>();

    public Optional<Ticket> findById(TicketId id) {
        return ticketList.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst();
    }

    public void save(Ticket ticket) {
        ticketList.add(ticket);
    }

    public void delete(Ticket ticket) {
        ticketList.remove(ticket);
    }
}
