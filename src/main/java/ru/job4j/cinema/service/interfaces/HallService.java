package ru.job4j.cinema.service.interfaces;

import ru.job4j.cinema.model.Hall;

public interface HallService {

    Hall getHallById(int id);

    Hall getHallBySessionId(int id);
}
