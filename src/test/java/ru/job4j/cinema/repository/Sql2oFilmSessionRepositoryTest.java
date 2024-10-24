package ru.job4j.cinema.repository;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oFilmSessionRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);
    }

    @AfterEach
    public void clearFilmSessions() {
        var filmSessions = sql2oFilmSessionRepository.getAllFilmSessions();
        filmSessions.forEach(filmSession ->
                sql2oFilmSessionRepository.deleteById(filmSession.getId()));
    }

//    @Deprecated
//    @Test
//    public void whenSaveFilmSessionAndDeleteItThenGetTrue() {
//        FilmSession filmSession = sql2oFilmSessionRepository.save(
//                new FilmSession(
//                        1,
//                        1,
//                        1,
//                        LocalDateTime.now(),
//                        LocalDateTime.now(),
//                        1500)
//        );
//
//        assertThat(sql2oFilmSessionRepository.deleteById(filmSession.getId())).isTrue();
//
//    }

    @Test
    public void whenSaveFilmSessionThenGetIt() {
        var filmSession = sql2oFilmSessionRepository.save(
                new FilmSession(
                        0,
                        1,
                        1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1500)
        );
        assertThat(sql2oFilmSessionRepository.getFilmSessionById(filmSession.getId()))
                .isNotNull();
    }
}

