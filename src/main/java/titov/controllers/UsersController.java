package titov.controllers;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import titov.requestDto.RequestUpdatedUserDto;
import titov.requestDto.RequestUserDto;
import titov.responseDto.ResponseUserDto;
import titov.services.user.UserService;
import titov.services.user.UserServiceImpl;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import static titov.utils.Constants.*;

@Data
@Slf4j
public class UsersController extends HttpServlet {
    private final Gson gson = new Gson();
    private final UserService userService = new UserServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("Получен запрос на создание нового пользователя.");
        try {
            RequestUserDto requestUserDto = gson.fromJson(req.getReader(), RequestUserDto.class);
            ResponseUserDto responseUserDto = userService.create(requestUserDto);
            resp.setStatus(CREATED_CODE);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseUserDto));
            log.debug("Клиенту отправлены данные: {}.", responseUserDto);
        } catch (IOException | SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long userId = Long.valueOf(req.getParameter("id"));
        log.debug("Получен запрос на получение данных пользователя по id = {}.", userId);
        try {
            ResponseUserDto responseUserDto = userService.getById(userId);
            resp.setStatus(SUCCESS_CODE);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseUserDto));
            log.debug("Клиенту отправлены данные: {}.", responseUserDto);
        } catch (IOException | SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("Получен запрос на обновление данных пользователя.");
        try {
            Long userId = Long.valueOf(req.getHeader("id"));
            RequestUpdatedUserDto requestUserDto = gson.fromJson(req.getReader(), RequestUpdatedUserDto.class);
            ResponseUserDto responseUserDto = userService.update(requestUserDto, userId);
            resp.setStatus(SUCCESS_CODE);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseUserDto));
            log.debug("Клиенту отправлены данные: {}.", responseUserDto);
        } catch (IOException | SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        Long userId = Long.valueOf(req.getHeader("id"));
        log.debug("Получен запрос на удаление данных пользователя с id = {}.", userId);
        try {
            userService.delete(userId);
            resp.setStatus(SUCCESS_CODE);
            log.debug("Клиент проинформирован об удалении пользователя.");
        } catch (SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }

}