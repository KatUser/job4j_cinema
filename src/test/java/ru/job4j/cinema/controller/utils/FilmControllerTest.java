package ru.job4j.cinema.controller.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.service.interfaces.FilmService;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmControllerTest {

    private FilmController filmController;

    @Mock
    private FilmService mockFilmService;

    @BeforeEach
    public void initServices() {
        filmController
                = new FilmController(mockFilmService);
    }

    @DisplayName("Получаем список всех фильмов")
    @Test
    void whenRequestFilmsPageThenReceiveAllFilms() {
        var film1 = new Film(
                1,
                "name1",
                "description1",
                2000,
                1,
                0,
                100,
                1);
        var film2 = new Film(
                2,
                "name2",
                "description2",
                2001,
                2,
                0,
                101,
                2);

        var expectedFilms = List.of(film1, film2);

        when(mockFilmService.findAll()).thenReturn(expectedFilms);

        var model = new ConcurrentModel();
        var view = filmController.getAllFilms(model);
        var actualFilms = model.getAttribute("film");

        assertThat(view).isEqualTo("films/list");
        assertThat(actualFilms).isEqualTo(expectedFilms);
    }

    @DisplayName("Переход на страницу фильма по его id")
    @Test
    void whenRequestAFilmByIdThenGetItsPage() {
        var film1 = new FilmDto(
                1,
                "name1",
                "description1",
                2000,
                "test",
                0,
                100,
                1);

        when(mockFilmService.findById(film1.getId())).thenReturn(film1);

        var model = new ConcurrentModel();
        var view = filmController.showOneFilmInfo(model, 1);
        var actualFilmDto = model.getAttribute("filmdto");

        assertThat(view).isEqualTo("films/one");
        assertThat(actualFilmDto).isEqualTo(film1);

    }

    @DisplayName("Когда нет фильма с заданным id")
    @Test
    void whenRequestedFilmDoesNotExistThenGetErrorMessage() {

        when(mockFilmService.findById(1)).thenReturn(null);

        var model = new ConcurrentModel();
        var view = filmController.showOneFilmInfo(model, 1);
        var actualFilmDto = model.getAttribute("filmdto");

        assertThat(view).isEqualTo("errors/404");
        assertThat(actualFilmDto).isEqualTo(null);
        assertThat(model.getAttribute("message"))
                .isEqualTo("Такого фильма нет");
    }

}