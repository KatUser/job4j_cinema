package ru.job4j.cinema.service;

import org.springframework.stereotype.Service;
import ru.job4j.cinema.model.Hall;
import ru.job4j.cinema.repository.HallRepository;

@Service
public class SimpleHallService implements HallService {

    private final HallRepository hallRepository;

    public SimpleHallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public Hall getHallById(int id) {
        return hallRepository.getHallById(id);
    }

    @Override
    public Hall getHallBySessionId(int id) {
        return hallRepository.getHallBySessionId(id);
    }


}
