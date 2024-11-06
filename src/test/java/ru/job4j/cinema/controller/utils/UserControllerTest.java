package ru.job4j.cinema.controller.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.ui.ConcurrentModel;
import ru.job4j.cinema.model.User;
import ru.job4j.cinema.service.interfaces.UserService;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    private UserController userController;
    @Mock
    private UserService mockUserService;

    @BeforeEach
    public void initServices() {
        userController = new UserController(
                mockUserService
        );
    }
    @DisplayName("Проверка получения стр регистрации")
    @Test
    void whenRequestRegistrationPageThenGetIt() {

        assertThat(userController.getRegistrationPage())
                .isEqualTo("users/register");
    }

    @DisplayName("Проверка успешной регистрации")
    @Test
    void whenRegistrationIsOk() {

        User user = new User(1, "fullName", "email", "password");
        when(mockUserService.save(user)).thenReturn(Optional.of(user));

        var model = new ConcurrentModel();
        var view = userController.register(user, model);

        assertThat(view).isEqualTo("redirect:/filmsessions");
    }

    @DisplayName("Когда не смог зарегиться")
    @Test
    void whenRegistrationFails() {

        User user = new User(1, "fullName", "email", "password");

        when(mockUserService.save(user)).thenReturn(Optional.empty());

        var model = new ConcurrentModel();
        var view = userController.register(user, model);

        assertThat(view).isEqualTo("errors/404");
        assertThat(model.getAttribute("message"))
                .isEqualTo("Пользователь с таким email уже существует");
    }

    @DisplayName("Получаем страницу авторизации")
    @Test
    void whenRequestLoginPageThenGetIt() {
        assertThat(userController.getLoginPage())
                .isEqualTo("users/login");
    }

    @DisplayName("Попадаем на киносеансы при успешной авторизации")
    @Test
    void whenUserLogsInHeGoesToFilmSessionsPage() {

        User user = new User(1, "fullName", "email", "password");

        when(mockUserService.findByEmailAndPassword(user.getEmail(),
                        user.getPassword())).thenReturn(Optional.of(user));

        MockHttpServletRequest request = new MockHttpServletRequest();
        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("redirect:/filmsessions");

    }

    @DisplayName("Когда не смог авторизоваться")
    @Test
    void whenUserFailsToLogIn() {

        User user = new User(1, "fullName", "email", "password");

        when(mockUserService.findByEmailAndPassword(user.getEmail(),
                user.getPassword())).thenReturn(Optional.empty());

        MockHttpServletRequest request = new MockHttpServletRequest();

        var model = new ConcurrentModel();
        var view = userController.loginUser(user, model, request);

        assertThat(view).isEqualTo("users/login");
        assertThat(model.getAttribute("error"))
                .isEqualTo("Почта или пароль введены неверно");
    }

    @DisplayName("Когда вышел из аккаунта")
    @Test
    void whenUserLogsOut() {
        MockHttpSession mockHttpSession = new MockHttpSession();
        assertThat(userController.logout(mockHttpSession))
                .isEqualTo("redirect:/users/login");
    }
}