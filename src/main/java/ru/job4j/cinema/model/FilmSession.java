package ru.job4j.cinema.model;

import java.time.LocalDateTime;
import java.util.Objects;

public class FilmSession {
    private int id;
    private int filmId;
    private int hallsId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int price;

    public FilmSession(int id,
                       int filmId,
                       int hallsId,
                       LocalDateTime startTime,
                       LocalDateTime endTime,
                       int price) {
        this.id = id;
        this.filmId = filmId;
        this.hallsId = hallsId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public int getFilmId() {
        return filmId;
    }

    public int getHallsId() {
        return hallsId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public void setHallsId(int hallsId) {
        this.hallsId = hallsId;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSession that = (FilmSession) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
