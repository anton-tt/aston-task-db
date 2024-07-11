package titov.controllers;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import titov.requestDto.RequestCardDto;
import titov.responseDto.ResponseCardDto;
import titov.services.card.CardService;
import titov.services.card.CardServiceImpl;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static titov.utils.Constants.*;

@Data
@Slf4j
public class CardsController extends HttpServlet {
    private final Gson gson = new Gson();
    private final CardService cardService = new CardServiceImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("Получен запрос на создание новой карточки.");
        try {
            RequestCardDto requestCardDto = gson.fromJson(req.getReader(), RequestCardDto.class);
            Long ownerId = Long.valueOf(req.getHeader("id"));
            ResponseCardDto responseCardDto = cardService.create(requestCardDto, ownerId);
            resp.setStatus(CREATED_CODE);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseCardDto));
            log.debug("Ответ отправлен клиенту: {}.", responseCardDto);
        } catch (IOException | SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Long cardId = Long.valueOf(req.getParameter("id"));
        log.debug("Получен запрос на получение данных карточки по id = {}.", cardId);
        try {
            ResponseCardDto responseCardDto = cardService.getById(cardId);
            resp.setStatus(SUCCESS_CODE);
            resp.setContentType("application/json");
            resp.getWriter().write(gson.toJson(responseCardDto));
            log.debug("Клиенту отправлены данные: {}.", responseCardDto);
        } catch (IOException | SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }
}
