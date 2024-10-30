package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.dto.FileDto;
import ru.job4j.cinema.dto.FilmDto;
import ru.job4j.cinema.model.Film;
import ru.job4j.cinema.repository.FilmRepository;

import java.util.Collection;

@Service
public class SimpleFilmService implements FilmService {

    private final FilmRepository filmRepository;

    private final FileService fileService;

    public SimpleFilmService(FilmRepository filmRepository,
                             FileService fileService) {

        this.filmRepository = filmRepository;

        this.fileService = fileService;
    }

    @Override
    public Film save(Film film, FileDto fileDto) {

        saveNewFile(film, fileDto);

        return filmRepository.save(film);
    }

    private void saveNewFile(Film film, FileDto fileDto) {

        var savedFile = fileService.save(fileDto);

        film.setFileId(savedFile.getId());
    }

    @Override
    public FilmDto findById(int id) {

        return filmRepository.findById(id);
    }

    @Override
    public Collection<Film> findAll() {

        return filmRepository.findAllFilms();
    }


    @Override
    public void deleteById(int id) {

        var file = findById(id);

        filmRepository.deleteById(id);

        fileService.deleteById(file.getFileId());

    }

}
