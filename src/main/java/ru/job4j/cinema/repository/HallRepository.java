package ru.job4j.cinema.repository;

import ru.job4j.cinema.model.Hall;

public interface HallRepository {

    Hall getHallById(int id);

    Hall getHallBySessionId(int id);
}
