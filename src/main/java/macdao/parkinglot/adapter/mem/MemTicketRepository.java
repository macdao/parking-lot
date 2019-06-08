package macdao.parkinglot.adapter.mem;

import macdao.parkinglot.domain.TicketRepository;
import macdao.parkinglot.domain.model.Ticket;
import macdao.parkinglot.domain.model.TicketId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MemTicketRepository implements TicketRepository {
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
