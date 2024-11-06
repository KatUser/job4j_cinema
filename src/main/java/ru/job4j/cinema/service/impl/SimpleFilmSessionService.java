package ru.job4j.cinema.service.impl;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FilmSessionDto;
import ru.job4j.cinema.repository.interfaces.FilmSessionRepository;
import ru.job4j.cinema.service.interfaces.FilmSessionService;

import java.util.Collection;

@Service
public class SimpleFilmSessionService implements FilmSessionService {

    private final FilmSessionRepository filmSessionRepository;


    public SimpleFilmSessionService(FilmSessionRepository filmSessionRepository) {
        this.filmSessionRepository = filmSessionRepository;
    }

    public Collection<FilmSessionDto> getAllFilmSessions() {
        return filmSessionRepository.getAllFilmSessions();
    }

    public FilmSessionDto findFilmSessionById(int id) {
        return filmSessionRepository.getFilmSessionById(id);
    }

    public boolean deleteById(int id) {
        return filmSessionRepository.deleteById(id);
    }

}
