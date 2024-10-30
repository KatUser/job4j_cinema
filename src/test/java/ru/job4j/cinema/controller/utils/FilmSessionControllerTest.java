package ru.job4j.cinema.controller.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class FilmSessionControllerTest {

    private FilmSessionController filmSessionController;

    @Mock
    private FilmSessionService mockFilmSessionService;

    @Mock
    private FilmService mockFilmService;

    @Mock
    private HallService mockHallService;

    @BeforeEach
    public void initServices() {
        filmSessionController = new FilmSessionController(
                mockFilmSessionService,
                mockFilmService,
                mockHallService
        );
    }

    @DisplayName("Получаем список всех киносеансов")
    @Test
    void whenRequestAllFilmSessionsThenReceiveThem() {
        var filmSessionDto1 = new FilmSessionDto(
                1,
                1,
                "1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "hallName",
                1000,
                1);

        var filmSessionDto2 = new FilmSessionDto(
                2,
                2,
                "2",
                "description2",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "hallName2",
                2000,
                2);

        var expectedFilmSessions = List.of(filmSessionDto1, filmSessionDto2);

        when(mockFilmSessionService.getAllFilmSessions()).thenReturn(expectedFilmSessions);

        var model = new ConcurrentModel();
        var view = filmSessionController.getAll(model);
        var actualFilmSessions = model.getAttribute("filmsessiondtos");

        assertThat(view).isEqualTo("filmsessions/list");
        assertThat(expectedFilmSessions).isEqualTo(actualFilmSessions);
    }

    @DisplayName("Получаем инфу о киносеансе по его id")
    @Test
    void whenRequestAFilmSessionThenReceiveIt() {
        var expectedFilmSessionDto = new FilmSessionDto(
                1,
                1,
                "1",
                "description",
                LocalDateTime.now(),
                LocalDateTime.now(),
                "hallName",
                1000,
                1);

        when(mockFilmSessionService.findFilmSessionById(expectedFilmSessionDto.getId()))
                .thenReturn(expectedFilmSessionDto);

        when(mockHallService.getHallBySessionId(1)).thenReturn(
                new Hall(1,
                        "test",
                        5,
                        5,
                        "testHall")
        );
        var request = new MockHttpServletRequest();
        var model = new ConcurrentModel();
        var view = filmSessionController.showOneFilmSessionInfo(
                model,
                1,
                request);
        var actualFilmSessionDto = model.getAttribute("filmsessiondto");

        assertThat(view).isEqualTo("filmsessions/one");
        assertThat(actualFilmSessionDto).isEqualTo(expectedFilmSessionDto);
    }

}