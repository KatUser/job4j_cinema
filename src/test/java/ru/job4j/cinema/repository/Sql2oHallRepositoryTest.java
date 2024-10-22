package ru.job4j.cinema.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.job4j.cinema.configuration.DatasourceConfiguration;
import ru.job4j.cinema.model.Hall;

import java.util.Properties;

import static org.assertj.core.api.Assertions.*;

class Sql2oHallRepositoryTest {
    private static Sql2oHallRepository sql2oHallRepository;

    @BeforeAll
    public static void initRepositories() throws Exception {
        var properties = new Properties();
        try (var inputStream = Sql2oHallRepositoryTest.class.getClassLoader()
                .getResourceAsStream("connection.properties")) {
            properties.load(inputStream);
        }
        var url = properties.getProperty("datasource.url");
        var username = properties.getProperty("datasource.username");
        var password = properties.getProperty("datasource.password");

        var configuration = new DatasourceConfiguration();
        var datasource = configuration.connectionPool(url, username, password);
        var sql2o = configuration.databaseClient(datasource);

        sql2oHallRepository = new Sql2oHallRepository(sql2o);
    }

    @AfterEach
    public void clearHalls() {
        var halls = sql2oHallRepository.getAllHalls();
        halls.forEach(hall ->
                sql2oHallRepository.deleteById(hall.getId()));
    }

    @Test
    public void whenSaveHallThenGetItById() {
        var hall = sql2oHallRepository.saveHall(
                new Hall(0,
                        "name",
                        1,
                        1,
                        "description")
        );

        var allHalls = sql2oHallRepository.getAllHalls();
        assertThat(allHalls).contains(hall);
    }
}