package ru.job4j.cinema.repository.impl;

import org.springframework.stereotype.Repository;
import ru.job4j.cinema.model.File;
import ru.job4j.cinema.repository.interfaces.FileRepository;

import java.util.Optional;

@Repository
public class Sql2oFileRepository implements FileRepository {
    @Override
    public File save(File file) {
        return null;
    }

    @Override
    public Optional<File> findById(int id) {
        return Optional.empty();
    }

    @Override
    public void deleteById(int id) {
    }
}
