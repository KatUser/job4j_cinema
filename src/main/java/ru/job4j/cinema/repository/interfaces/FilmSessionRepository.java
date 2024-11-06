package ru.job4j.cinema.repository.interfaces;

import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.model.FilmSession;

import java.util.Collection;

public interface FilmSessionRepository {

    Collection<FilmSessionDto> getAllFilmSessions();

    FilmSessionDto getFilmSessionById(int id);

    boolean deleteById(int id);

    FilmSession save(FilmSession filmSession);
}
