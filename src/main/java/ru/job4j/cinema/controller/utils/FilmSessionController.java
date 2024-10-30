package ru.job4j.cinema.controller.utils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.FilmService;
import ru.job4j.cinema.service.FilmSessionService;
import ru.job4j.cinema.service.HallService;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

@Controller
@RequestMapping("/filmsessions")
public class FilmSessionController {

    private final FilmSessionService filmSessionService;

    private final FilmService filmService;

    private final HallService hallService;

    public FilmSessionController(FilmSessionService filmSessionService,
                                 FilmService filmService,
                                 HallService hallService) {
        this.filmSessionService = filmSessionService;
        this.filmService = filmService;
        this.hallService = hallService;
    }

    @GetMapping
    String getAll(Model model) {

        model.addAttribute("filmsessiondtos",
                filmSessionService.getAllFilmSessions());

        return "filmsessions/list";
    }

    @GetMapping("/{id}")

    public String showOneFilmSessionInfo(Model model,
                                         @PathVariable int id,
                                         HttpServletRequest request) {

        var ticket = new Ticket();

        var foundFilmSession = filmSessionService.findFilmSessionById(id);
        var foundFilm = filmService.findById(foundFilmSession.getFilmId());
        var hall = hallService.getHallBySessionId(id);

        ticket.setSessionId(id);

        model.addAttribute("filmsessiondto", foundFilmSession);
        model.addAttribute("filmdto", foundFilm);
        model.addAttribute("rowCount", hall.getRowCount());
        model.addAttribute("placeCount", hall.getPlaceCount());
        model.addAttribute("ticket", ticket);

        var httpSession = request.getSession();

        var user = Optional.ofNullable((User) httpSession.getAttribute("user"));

        user.ifPresent(value -> ticket.setUserId(value.getId()));

        return "filmsessions/one";
    }
}
