package ru.job4j.cinema.controller.utils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.Ticket;
import ru.job4j.cinema.service.TicketService;

@Controller
@RequestMapping("/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
     }


    @PostMapping("/buyticket")
    public String buyTicket(@ModelAttribute Ticket ticket,
                            Model model) {

        var savedTicket = ticketService.save(ticket);

        if (savedTicket.isEmpty()) {
            model.addAttribute("message", "Не купиль");
            return "errors/404";
        }

        ticket.setId(savedTicket.get().getId());

        return "tickets/one";
    }
}
