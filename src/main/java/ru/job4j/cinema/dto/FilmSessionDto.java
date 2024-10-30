package ru.job4j.cinema.dto;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;

public class FilmSessionDto {

    public static final Map<String, String> COLUMN_MAPPING
            = Map.of(
            "id", "id",
            "name", "name",
            "film_id", "filmId",
            "description", "description",
            "start_time", "startTime",
            "end_time", "endTime",
            "hall_name", "hallName",
            "price", "price",
            "halls_id", "hallsId"
    );

    private int id;
    private String name;
    private int filmId;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String hallName;
    private int price;
    private int hallsId;

    public FilmSessionDto(int id,
                          int filmId,
                          String name,
                          String description,
                          LocalDateTime startTime,
                          LocalDateTime endTime,
                          String hallName,
                          int price,
                          int hallsId) {
        this.id = id;
        this.filmId = filmId;
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.hallName = hallName;
        this.price = price;
        this.hallsId = hallsId;
    }

    public FilmSessionDto() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFilmId(){
        return filmId;
    }

    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getHallName() {
        return hallName;
    }

    public void setHallName(String hallName) {
        this.hallName = hallName;
    }

    public int getHallsId() {
        return hallsId;
    }

    public void setHallsId(int hallsId) {
        this.hallsId = hallsId;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmSessionDto that = (FilmSessionDto) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


}
