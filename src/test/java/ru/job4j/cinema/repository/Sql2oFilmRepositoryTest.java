package ru.job4j.cinema.repository;

import java.util.Properties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;


import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;

import static org.assertj.core.api.Assertions.*;

class Sql2oFilmRepositoryTest {

    private static Sql2oFilmRepository sql2oFilmRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @AfterEach
    public void clearFilms() {
        var films = sql2oFilmRepository.findAllFilms();
        films.forEach(film -> sql2oFilmRepository.deleteById(film.getId()));
    }

    @Test
    public void whenSaveThenGetSavedFilm() {
        var film = sql2oFilmRepository.save(
                new Film(0,
                        "TestFilm",
                        "description",
                        2000,
                        1,
                        1,
                        1,
                        1)
        );

        var savedFilms = sql2oFilmRepository.findAllFilms();

        assertThat(savedFilms).contains(film);
    }

    @Test
    public void whenSaveNothingThenNothingIsFound() {
        assertThat(sql2oFilmRepository.findAllFilms()).isEmpty();
    }

    @Test
    public void whenDeleteAllThenGetEmptyOptional() {
        var film = sql2oFilmRepository.save(
                new Film(0,
                        "TestFilm",
                        "description",
                        2000,
                        1,
                        1,
                        1,
                        1)
        );
        var isDeleted = sql2oFilmRepository.deleteById(film.getId());
        var savedAndDeletedFilm = sql2oFilmRepository.findById(film.getId());
        assertThat(isDeleted).isTrue();
        assertThat(savedAndDeletedFilm).isNull();
    }

    @Test
    public void whenSaveFilmThenGetItById() {
        var film = sql2oFilmRepository.save(
                new Film(0,
                        "TestFilm",
                        "description",
                        2000,
                        1,
                        1,
                        1,
                        1)
        );

        var savedFilm = sql2oFilmRepository.findById(film.getId());
        assertThat(film.getId()).isEqualTo(savedFilm.getId());
    }

    @Test
    public void whenSaveFilmThenGetItByName() {
        var film = sql2oFilmRepository.save(
                new Film(0,
                        "TestFilm",
                        "description",
                        2000,
                        1,
                        1,
                        1,
                        1)
        );

        var savedFilm = sql2oFilmRepository.findByName(film.getName());
        assertThat(film.getName()).isEqualTo(savedFilm.getName());
    }
}