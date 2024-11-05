package ru.job4j.cinema.repository;


import org.junit.jupiter.api.*;


import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.model.FilmSession;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

class Sql2oFilmSessionRepositoryTest {

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    private static Sql2oFilmRepository sql2oFilmRepository;

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

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);
    }

    @AfterEach
    public void clearFilmSessions() {

        var filmSessions = sql2oFilmSessionRepository.getAllFilmSessions();

        filmSessions.forEach(filmSession ->
                sql2oFilmSessionRepository.deleteById(filmSession.getId()));
    }

    @BeforeEach
    public void clearFilmSessionsBefore() {

        var filmSessions = sql2oFilmSessionRepository.getAllFilmSessions();

        filmSessions.forEach(filmSession ->
                sql2oFilmSessionRepository.deleteById(filmSession.getId()));
    }



    @DisplayName("Получаем сессию по id")
    @Test
    public void whenSaveFilmSessionThenGetItById() {

        var savedFilm = sql2oFilmRepository.save(new Film(0,
                "name",
                "desc",
                2001,
                1,
                1,
                1,
                1));

        var filmSession = sql2oFilmSessionRepository.save(
                new FilmSession(
                        0,
                        savedFilm.getId(),
                        1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1500)
        );

        assertThat(sql2oFilmSessionRepository
                .getFilmSessionById(filmSession.getId()))
                .isNotNull();
    }


    @DisplayName("Получение несуществующей сессии")
    @Test
    public void whenGettingNonExistingSessionIdThenReceiveNull() {
        assertThat(sql2oFilmSessionRepository.getFilmSessionById(1000))
                .isNull();
    }

    @DisplayName("Получаем список сессий")
    @Test
    public void whenGettingListOfSessionsThenReceiveIt() {

        var savedFilm = sql2oFilmRepository.save(new Film(0,
                "name",
                "desc",
                2001,
                1,
                1,
                1,
                1));

        var savedFilmSession1 =  sql2oFilmSessionRepository.save(
                new FilmSession(
                        0,
                        savedFilm.getId(),
                        1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1500)
        );

        var savedFilmSession2 = sql2oFilmSessionRepository.save(
                new FilmSession(
                        0,
                        savedFilm.getId(),
                        1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1500)
        );

        var allFilmSessions = sql2oFilmSessionRepository.getAllFilmSessions();

        assertThat(allFilmSessions).hasSize(2);

        assertThat(sql2oFilmSessionRepository
                .getFilmSessionById(savedFilmSession1.getId())).isNotNull();

        assertThat(sql2oFilmSessionRepository
                .getFilmSessionById(savedFilmSession2.getId())).isNotNull();
    }
}

