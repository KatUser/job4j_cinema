package ru.job4j.cinema.service.interfaces;

import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;

import java.util.Collection;

public interface FilmService {

    Film save(Film film, FileDto fileDto);

    void deleteById(int id);

    FilmDto findById(int id);

    Collection<Film> findAll();
}
