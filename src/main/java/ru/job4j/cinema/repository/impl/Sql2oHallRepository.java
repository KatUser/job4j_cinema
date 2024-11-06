package ru.job4j.cinema.repository.impl;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.interfaces.HallRepository;

import java.util.Collection;

@Repository
public class Sql2oHallRepository implements HallRepository {

    private final Sql2o sql2o;

    public Sql2oHallRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }


    @Override
    public Hall getHallById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    SELECT * FROM halls
                    WHERE id = :id
                    """);

            query.addParameter("id", id);

            return query.setColumnMappings(Hall.COLUMN_MAPPING)
                    .executeAndFetchFirst(Hall.class);
        }
    }

    public Hall getHallBySessionId(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                            SELECT * FROM halls
                            WHERE id =
                            (SELECT halls_id
                            FROM film_sessions WHERE "id" = :id)
                            """);

            query.addParameter("id", id);

            return query.setColumnMappings(Hall.COLUMN_MAPPING)
                    .executeAndFetchFirst(Hall.class);
        }
    }

    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    DELETE
                    FROM halls
                    WHERE id = :id
                    """);
            query.addParameter("id", id);

            query.executeUpdate();

            return connection.getResult() != 0;
        }
    }

    public Collection<Hall> getAllHalls() {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                            SELECT * FROM
                            halls
                            ;
                            """);

            return query.setColumnMappings(Hall.COLUMN_MAPPING)
                    .executeAndFetch(Hall.class);
        }
    }

    public Hall saveHall(Hall hall) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO halls
                    ("name", row_count, place_count, description)
                    values(:name, :rowCount, :placeCount, :description)
                    """;

            var query = connection.createQuery(sql, true)
                    .addParameter("name", hall.getName())
                    .addParameter("rowCount", hall.getRowCount())
                    .addParameter("placeCount", hall.getPlaceCount())
                    .addParameter("description", hall.getDescription());

            int generatedId = query.executeUpdate().getKey(Integer.class);

            hall.setId(generatedId);

            return hall;
        }

    }

}
