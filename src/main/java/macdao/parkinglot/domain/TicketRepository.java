package macdao.parkinglot.domain;

import macdao.parkinglot.domain.model.Ticket;
import macdao.parkinglot.domain.model.TicketId;

import java.util.Optional;

public interface TicketRepository {

    Optional<Ticket> findById(TicketId id);

    void save(Ticket ticket);

    void delete(Ticket ticket);
}
