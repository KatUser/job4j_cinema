package ru.job4j.cinema.controller.utils;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String getRegistrationPage() {
        return "users/register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user,
                           Model model) {

        var savedUser = userService.save(user);

        if (savedUser.isEmpty()) {

            model.addAttribute("message",
                    "Пользователь с таким email уже существует");
            return "errors/404";
        }
        return "redirect:/filmsessions";
    }

    @GetMapping("login")
    public String getLoginPage() {
        return "users/login";
    }

    @PostMapping("login")
    public String loginUser(@ModelAttribute User user,
                            Model model,
                            HttpServletRequest request) {

        var userOptional = userService.findByEmailAndPassword(
                user.getEmail(), user.getPassword()
        );

        if (userOptional.isEmpty()) {
            model.addAttribute("error",
                    "Почта или пароль введены неверно");
            return "users/login";
        }
        int id = userOptional.get().getId();
        var httpSession = request.getSession();

        httpSession.setAttribute("user", userOptional.get());
        httpSession.setAttribute("userId", id);

        return "redirect:/filmsessions";
    }

    @GetMapping("/logout")
    public String logout(HttpSession httpSession) {

        httpSession.invalidate();

        return "redirect:/users/login";
    }
}