package titov.repositories;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import titov.mappers.CardMapper;
import titov.models.Card;
import titov.utils.DataBaseConnection;
import java.sql.*;

@Data
@Slf4j
public class CardRepository {
    private final static String INSERT_CARD = "INSERT INTO cards (name, link, created, owner_id) " +
            "VALUES (?, ?, ?, ?)";
    private final static String SELECT_CARD = "SELECT name, link, created, owner_id " +
            "FROM cards WHERE id = ?";
    private final static String SELECT_LIKE = "SELECT card_id FROM likes WHERE user_id = ?";
    private final static String LIKE_CARD = "INSERT INTO likes (card_id, user_id) VALUES (?, ?);";

    private final static String DISLIKE_CARD = "DELETE FROM likes WHERE card_id = ? AND user_id = ?";

    public Card save(Card card) throws SQLException {
        String cardName = card.getName();
        log.debug("Добавление в БД новой карточки с name: {}.", cardName);
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(INSERT_CARD, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, card.getName());
            prepareStatement.setString(2, card.getLink());
            prepareStatement.setDate(3,  Date.valueOf(card.getCreated()));
            prepareStatement.setLong(4, card.getOwnerId());
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next())
                card.setId(generatedKeys.getLong("id"));
            log.debug("Данные новой карточки добавлены в БД.");
            return card;
        } catch (SQLException e) {
            log.error("Ошибка в работе БД.");
            throw new SQLException();
        }
    }

    public Card findById(Long id) throws SQLException {
        log.info("Поиск в БД карточки с id = {}", id);
        Card card = null;
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(SELECT_CARD)) {
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                card = CardMapper.toCard(
                        id,
                        resultSet.getString("name"),
                        resultSet.getString("link"),
                        resultSet.getDate("created").toLocalDate(),
                        resultSet.getLong("owner_id"));
            }
            log.debug("Данные карточки получены из БД по id.");
            return card;
        } catch (SQLException e) {
            log.error("Ошибка в работе БД.");
            throw new SQLException();
        }
    }

    public void likeCard(Long cardId, Long userId) throws SQLException {
        log.info("Сохранение в БД лайка от пользователя с id = {} карточке с id = {}", userId, cardId);
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(LIKE_CARD)) {
            prepareStatement.setLong(1, cardId);
            prepareStatement.setLong(2, userId);
            prepareStatement.executeUpdate();
            log.debug("Лайк внесён в БД.");
        } catch (SQLException e) {
            log.error("Ошибка в работе БД.");
            throw new SQLException();
        }
    }

    public void dislikeCard(Long cardId, Long userId) throws SQLException {
        log.info("Удаление из БД лайка от пользователя с id = {} карточке с id = {}", userId, cardId);
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(DISLIKE_CARD)) {
            prepareStatement.setLong(1, cardId);
            prepareStatement.setLong(2, userId);
            prepareStatement.executeUpdate();
            log.debug("Лайк удалён из БД.");
        } catch (SQLException e) {
            log.error("Ошибка в работе БД.");
            throw new SQLException();
        }
    }

}