package ru.job4j.cinema.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Ticket;

import java.util.Collection;
import java.util.Optional;

@Repository
public class Sql2oTicketRepository implements TicketRepository {

    private static final Logger LOG = LoggerFactory.getLogger(Sql2oTicketRepository.class.getName());

    private final Sql2o sql2o;

    public Sql2oTicketRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public Optional<Ticket> save(Ticket ticket) {
        try (var connection = sql2o.open()) {

            var sql = """
                    INSERT INTO tickets
                    (session_id, row_number, place_number, user_id)
                    VALUES
                    (:sessionId, :rowNumber, :placeNumber, :userId)
                    """;

            var query = connection.createQuery(sql, true)
                    .addParameter("sessionId", ticket.getSessionId())
                    .addParameter("rowNumber", ticket.getRowNumber())
                    .addParameter("placeNumber", ticket.getPlaceNumber())
                    .addParameter("userId", ticket.getUserId());

            int generatedId = query.executeUpdate().getKey(Integer.class);

            ticket.setId(generatedId);

            return Optional.of(ticket);

        } catch (Exception e) {
            LOG.error("Ошибка сохранения билета");
        }
        return Optional.empty();
    }

    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    DELETE
                    FROM tickets
                    WHERE id = :id
                    """);
            query.addParameter("id", id);

            query.executeUpdate();

            return connection.getResult() != 0;
        }
    }

    public Collection<Ticket> getAllTickets() {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                            SELECT * FROM
                            tickets
                            ;
                            """);

            return query.setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetch(Ticket.class);
        }
    }

    public Optional<Ticket> getTicketById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    SELECT * FROM tickets
                    WHERE id = :id
                    """);

            query.addParameter("id", id);

            return Optional.ofNullable(query
                    .setColumnMappings(Ticket.COLUMN_MAPPING)
                    .executeAndFetchFirst(Ticket.class));
        }
    }
}
