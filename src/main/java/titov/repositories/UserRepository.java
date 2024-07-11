package titov.repositories;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import titov.mappers.UserMapper;
import titov.models.User;
import titov.utils.DataBaseConnection;
import java.sql.*;

@Data
@Slf4j
public class UserRepository {
    private final static String INSERT_USER = "INSERT INTO users (name, about, avatar, email, password) " +
            "VALUES (?, ?, ?, ?, ?)";
    private final static String SELECT_USER = "SELECT name, about, avatar, email, password " +
            "FROM users WHERE id = ?";
    private final static String UPDATE_USER = "UPDATE users SET name = ?, about = ?, avatar = ?, email = ? WHERE id = ?";
    private final static String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public User save(User user) throws SQLException {
        String userEmail = user.getEmail();
        log.debug("Добавление в БД нового пользователя с email: {}.", userEmail);
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getAbout());
            prepareStatement.setString(3, user.getAvatar());
            prepareStatement.setString(4, userEmail);
            prepareStatement.setString(5, user.getPassword());
            prepareStatement.executeUpdate();
            ResultSet generatedKeys = prepareStatement.getGeneratedKeys();
            if (generatedKeys.next())
                user.setId(generatedKeys.getLong("id"));
            log.debug("Данные нового пользователя добавлены в БД.");
            return user;
        } catch (SQLException e) {
            log.error("Ошибка в работе БД при сохранении пользователя.");
            throw new SQLException();
        }
    }

    public User findById(Long id) throws SQLException {
        log.info("Поиск в БД пользователя с id = {}", id);
        User user = null;
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(SELECT_USER)) {
            prepareStatement.setLong(1, id);
            ResultSet resultSet = prepareStatement.executeQuery();
            if (resultSet.next()) {
                user = UserMapper.toUser(
                        id,
                        resultSet.getString("name"),
                        resultSet.getString("about"),
                        resultSet.getString("avatar"),
                        resultSet.getString("email"),
                        resultSet.getString("password"));
            }
            log.debug("Данные пользователя получены из БД по id.");
            return user;
        } catch (SQLException e) {
            log.error("Ошибка в работе БД при получении данных по id.");
            throw new SQLException();
        }
    }

    public User update(User user) throws SQLException {
        Long userId = user.getId();
        log.debug("Обновление в БД данных пользователя с id = {}.", userId);
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(UPDATE_USER)) {
            prepareStatement.setString(1, user.getName());
            prepareStatement.setString(2, user.getAbout());
            prepareStatement.setString(3, user.getAvatar());
            prepareStatement.setString(4, user.getEmail());
            prepareStatement.setLong(5, userId);
            prepareStatement.executeUpdate();
            log.debug("Данные пользователя обновлены в БД.");
            return user;
        } catch (SQLException e) {
            log.error("Ошибка в работе БД.");
            throw new SQLException();
        }
    }

    public void remove(Long id) throws SQLException {
        log.debug("Удаление пользователя из БД с id = {}.", id);
        try (Connection conn = DataBaseConnection.connect();
             PreparedStatement prepareStatement = conn.prepareStatement(DELETE_USER)) {
            prepareStatement.setLong(1, id);
            prepareStatement.executeUpdate();
            log.debug("Данные пользователя удалены из БД.");
        } catch (SQLException e) {
            log.error("Ошибка в работе БД.");
            throw new SQLException();
        }
    }

}