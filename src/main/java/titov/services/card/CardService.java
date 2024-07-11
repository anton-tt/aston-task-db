package titov.services.card;

import titov.requestDto.RequestCardDto;
import titov.responseDto.ResponseCardDto;
import java.sql.SQLException;

public interface CardService {
    ResponseCardDto create(RequestCardDto cardDto, Long userId) throws SQLException;
    ResponseCardDto getById(Long cardId) throws SQLException;
    void like(Long cardId, Long userId) throws SQLException;
    void dislike(Long cardId, Long userId) throws SQLException;
}
