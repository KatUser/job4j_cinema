package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.*;

import java.time.LocalDateTime;
import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oTicketRepositoryTest {

    private static Sql2oTicketRepository sql2oTicketRepository;

    private static Sql2oUserRepository sql2oUserRepository;

    private static Sql2oFilmSessionRepository sql2oFilmSessionRepository;

    private static Sql2oFilmRepository sql2oFilmRepository;


    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oTicketRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oTicketRepository = new Sql2oTicketRepository(sql2o);

        sql2oUserRepository = new Sql2oUserRepository(sql2o);

        sql2oFilmSessionRepository = new Sql2oFilmSessionRepository(sql2o);

        sql2oFilmRepository = new Sql2oFilmRepository(sql2o);

    }

    @AfterEach
    public void clearTickets() {

        var tickets = sql2oTicketRepository.getAllTickets();

        tickets.forEach(ticket
                -> sql2oTicketRepository.deleteById(ticket.getId()));
    }

    @AfterEach
    public void clearUsers() {

        sql2oUserRepository.deleteAllUsers();
    }

    @AfterEach
    public void clearFilmSessions() {

        var filmSessions = sql2oFilmSessionRepository.getAllFilmSessions();

        filmSessions.forEach(fs -> sql2oFilmSessionRepository.deleteById(fs.getId()));
    }

    @AfterEach
    public void clearFilms() {
        var films = sql2oFilmRepository.findAllFilms();

        films.forEach(film -> sql2oFilmRepository.deleteById(film.getId()));
    }

    @DisplayName("Проверяем сохранение билета в БД")
    @Test
    public void whenSaveTicketThenGetIt() {

        var user = sql2oUserRepository.save(
                new User(
                        0,
                        "test",
                        "test@test.ru",
                        "test"
                ));

        var film = sql2oFilmRepository.save(
                new Film(
                        1,
                        "name",
                        "description",
                        2000, 1,
                        1,
                        1,
                        1
                ));

        var filmSession = sql2oFilmSessionRepository.save(new FilmSession(
                1,
                film.getId(),
                1,
                LocalDateTime.now(),
                LocalDateTime.now(),
                1
        ));

        var ticket = sql2oTicketRepository.save(
                new Ticket(
                        0,
                        filmSession.getId(),
                        1,
                        1,
                        user.get().getId()
                ));

        var savedTickets = sql2oTicketRepository.getAllTickets();

        assertThat(savedTickets).contains(ticket.get());
    }


    @DisplayName("Запрашиваем в БД несуществующий билет")
    @Test
    public void whenNoSavedTicketThenCannotGetIt() {

        var ticket = sql2oTicketRepository.getTicketById(100000);

        assertThat(ticket).isEmpty();
    }

    @DisplayName("Проверяем сохранение и получение нескольких билетов")
    @Test
    public void whenSaveSeveralTicketsThenGetAll() {

        var user = sql2oUserRepository.save(
                new User(
                        0,
                        "test",
                        "test@test.ru",
                        "test"
                ));

        var film = sql2oFilmRepository.save(
                new Film(
                        1,
                        "name",
                        "description",
                        2000,
                        1,
                        1,
                        1,
                        1
                ));

        var filmSession = sql2oFilmSessionRepository.save(
                new FilmSession(1,
                        film.getId(),
                        1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1
                ));

        sql2oTicketRepository.save(
                new Ticket(0,
                        filmSession.getId(),
                        1, 1,
                        user.get().getId()
                ));

        sql2oTicketRepository.save(
                new Ticket(0,
                        filmSession.getId(),
                        1,
                        2,
                        user.get().getId()
                ));

        var savedTickets = sql2oTicketRepository.getAllTickets();

        assertThat(savedTickets).hasSize(2);
    }

    @DisplayName("Проверяем, что нельзя купить уже купленный билет")
    @Test
    public void whenCannotBuyTicketForTheSamePlace() {
        var user = sql2oUserRepository.save(
                new User(0,
                        "test",
                        "test@test.ru",
                        "test"
                ));

        var film = sql2oFilmRepository.save(
                new Film(1,
                        "name",
                        "description",
                        2000,
                        1,
                        1,
                        1,
                        1
                ));

        var filmSession = sql2oFilmSessionRepository.save(
                new FilmSession(1,
                        film.getId(),
                        1,
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1
                ));

        var savedTicket = sql2oTicketRepository.save(
                new Ticket(0,
                        filmSession.getId(),
                        1,
                        1,
                        user.get().getId()
                ));

        var savedTicketOptional = savedTicket.get();

        assertThat(sql2oTicketRepository.save(
                new Ticket(
                        0,
                        savedTicketOptional.getId(),
                        savedTicketOptional.getRowNumber(),
                        savedTicketOptional.getPlaceNumber(),
                        savedTicketOptional.getUserId())))
                .isEmpty();
    }
}
