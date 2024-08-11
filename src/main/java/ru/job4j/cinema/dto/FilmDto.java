package ru.job4j.cinema.dto;

import java.util.Map;
import java.util.Objects;

public class FilmDto {

    private int id;
    private String name;
    private String description;
    private int year;
    private String genre;
    private int minimalAge;
    private int durationInMinutes;
    private int fileId;

    public static final Map<String, String> COLUMN_MAPPING
            = Map.of(
            "id", "id",
            "name", "name",
            "description", "description",
            "year", "year",
            "genre", "genre",
            "minimal_age", "minimalAge",
            "duration_in_minutes", "durationInMinutes",
            "file_id", "fileId"
    );


    public FilmDto(int id,
                   String name,
                   String description,
                   int year,
                   String genre,
                   int minimalAge,
                   int durationInMinutes,
                   int fileId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.year = year;
        this.genre = genre;
        this.minimalAge = minimalAge;
        this.durationInMinutes = durationInMinutes;
        this.fileId = fileId;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getYear() {
        return year;
    }

    public String getGenre() {
        return genre;
    }

    public int getMinimalAge() {
        return minimalAge;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public int getFileId() {
        return fileId;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setMinimalAge(int minimalAge) {
        this.minimalAge = minimalAge;
    }

    public void setDurationInMinutes(int durationInMinutes) {
        this.durationInMinutes = durationInMinutes;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        FilmDto filmDto = (FilmDto) o;
        return id == filmDto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
