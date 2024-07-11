package titov.controllers;

import com.google.gson.Gson;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import titov.services.card.CardService;
import titov.services.card.CardServiceImpl;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import static titov.utils.Constants.*;

@Data
@Slf4j
public class LikesController extends HttpServlet {
    private final Gson gson = new Gson();
    private final CardService cardService = new CardServiceImpl();

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("Получен запрос на постановку лайка карточке.");
        Long userId = Long.valueOf(req.getHeader("userId"));
        Long cardId = Long.valueOf(req.getParameter("cardId"));
        try {
            cardService.like(cardId, userId);
            resp.setStatus(SUCCESS_CODE);
        } catch (SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) {
        log.debug("Получен запрос на удаление лайка карточке.");
        Long userId = Long.valueOf(req.getHeader("userId"));
        Long cardId = Long.valueOf(req.getParameter("cardId"));
        try {
            cardService.dislike(cardId, userId);
            resp.setStatus(SUCCESS_CODE);
        } catch (SQLException e) {
            log.error("Ошибка при обработке данных.");
            resp.setStatus(INTERNAL_SERVER_CODE);
        }
    }

}