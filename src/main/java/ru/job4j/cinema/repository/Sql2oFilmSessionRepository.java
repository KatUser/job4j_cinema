package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;

@Repository
public class Sql2oFilmSessionRepository implements FilmSessionRepository {

    private final Sql2o sql2o;

    public Sql2oFilmSessionRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    public Collection<FilmSessionDto> getAllFilmSessions() {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                            SELECT
                            fs.id,
                            f.name,
                            start_time,
                            end_time,
                            h.name AS hall_name,
                            h.id as halls_id,
                            price
                            FROM film_sessions AS fs
                            INNER JOIN films AS f ON fs.film_id = f.id
                            INNER JOIN halls AS h ON fs.halls_id = h.id
                            ORDER BY start_time;
                            """);

            return query.setColumnMappings(FilmSessionDto.COLUMN_MAPPING)
                    .executeAndFetch(FilmSessionDto.class);
        }
    }

    public FilmSessionDto getFilmSessionById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                            SELECT
                            fs.id,
                            fs.halls_id as halls_id,
                            f.name,
                            fs.start_time,
                            fs.end_time,
                            h.name as hall_name,
                            fs.price
                            FROM film_sessions AS fs
                            INNER JOIN films AS f ON fs.film_id = f.id
                            INNER JOIN halls AS h ON fs.halls_id = h.id
                            where fs.id = :id
                            """);

            query.addParameter("id", id);

            return query.setColumnMappings(FilmSessionDto.COLUMN_MAPPING)
                    .executeAndFetchFirst(FilmSessionDto.class);
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    DELETE
                    FROM film_sessions
                    WHERE id = :id
                    """);
            query.addParameter("id", id);

            query.executeUpdate();

            return connection.getResult() != 0;
        }
    }

    @Override
    public FilmSession save(FilmSession filmSession) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO film_sessions
                    (film_id,
                    halls_id,
                    start_time,
                    end_time,
                    price)
                    values(:filmId,
                    :hallsId,
                    :startTime,
                    :endTime,
                    :price)
                    """;

            var query = connection.createQuery(sql, true)
                    .addParameter("filmId", filmSession.getFilmId())
                    .addParameter("hallsId", filmSession.getHallsId())
                    .addParameter("startTime", filmSession.getStartTime())
                    .addParameter("endTime", filmSession.getEndTime())
                    .addParameter("price", filmSession.getPrice());

            int generatedId = query.executeUpdate().getKey(Integer.class);

            filmSession.setId(generatedId);

            return filmSession;
        }
    }
}

