package ru.job4j.cinema.service;

import ru.job4j.cinema.dto.FilmSessionDto;

import java.util.Collection;

public interface FilmSessionService {
    Collection<FilmSessionDto> getAllFilmSessions();

    FilmSessionDto findFilmSessionById(int id);

    boolean deleteById(int id);

 }
