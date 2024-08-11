package ru.job4j.cinema.repository;

import org.springframework.stereotype.Repository;
import org.sql2o.Sql2o;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

import java.util.Collection;

@Repository
public class Sql2oFilmRepository implements FilmRepository {

    private final Sql2o sql2o;

    public Sql2oFilmRepository(Sql2o sql2o) {
        this.sql2o = sql2o;
    }

    @Override
    public Film save(Film film) {
        try (var connection = sql2o.open()) {
            var sql = """
                    INSERT INTO films
                    (name,
                    description,
                    "year",
                    genre_id,
                    minimal_age,
                    duration_in_minutes,
                    file_id)
                    values(:name,
                    :description,
                    :year,
                    :genreId,
                    :minimalAge,
                    :durationInMinutes,
                    :fileId)
                    """;

            var query = connection.createQuery(sql, true)
                    .addParameter("name", film.getName())
                    .addParameter("description", film.getDescription())
                    .addParameter("year", film.getYear())
                    .addParameter("genreId", film.getGenreId())
                    .addParameter("minimalAge", film.getMinimalAge())
                    .addParameter("durationInMinutes", film.getDurationInMinutes())
                    .addParameter("fileId", film.getFileId());

            int generatedId = query.executeUpdate().getKey(Integer.class);

            film.setId(generatedId);

            return film;
        }
    }

    @Override
    public boolean deleteById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    DELETE FROM films WHERE id = :id
                    """);
            query.addParameter("id", id);

            query.executeUpdate();

            return connection.getResult() != 0;
        }
    }

    @Override
    public FilmDto findById(int id) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                    SELECT
                    f.id,
                    f.name,
                    f.description,
                    f."year",
                    g.name as genre,
                    f.minimal_age,
                    f.duration_in_minutes,
                    f.file_id FROM films AS f
                    INNER JOIN genres AS g ON genre_id = g.id
                    WHERE f.id = :id
                    """);

            query.addParameter("id", id);

            return query.setColumnMappings(FilmDto.COLUMN_MAPPING)
                    .executeAndFetchFirst(FilmDto.class);
        }
    }

    @Override
    public FilmDto findByName(String name) {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery(
                    """
                    SELECT
                    f.id,
                    f.name,
                    f.description,
                    f."year",
                    g.name as genre,
                    f.minimal_age,
                    f.duration_in_minutes,
                    f.file_id FROM films AS f
                    INNER JOIN genres AS g ON genre_id = g.id
                    WHERE f.name = :name
                    """);

            query.addParameter("name", name);

            return query.setColumnMappings(FilmDto.COLUMN_MAPPING)
                    .executeAndFetchFirst(FilmDto.class);
        }
    }

    @Override
    public Collection<Film> findAll() {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    SELECT *
                    FROM films
                    """);

            return query.setColumnMappings(Film.COLUMN_MAPPING)
                    .executeAndFetch(Film.class);
        }
    }

    public Collection<Film> findAllFilms() {
        try (var connection = sql2o.open()) {

            var query = connection.createQuery("""
                    SELECT *
                    FROM films
                    """);

            return query.setColumnMappings(Film.COLUMN_MAPPING)
                    .executeAndFetch(Film.class);
        }
    }
}
