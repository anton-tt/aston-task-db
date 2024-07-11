package titov.services.card;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import titov.mappers.CardMapper;
import titov.models.Card;
import titov.models.User;
import titov.repositories.CardRepository;
import titov.repositories.UserRepository;
import titov.requestDto.RequestCardDto;
import titov.responseDto.ResponseCardDto;
import java.sql.SQLException;

@Data
@Slf4j
public class CardServiceImpl implements CardService {
    CardRepository cardRepository = new CardRepository();
    UserRepository userRepository = new UserRepository();

    @Override
    public ResponseCardDto create(RequestCardDto cardDto, Long userId) throws SQLException {
        Card cardData = CardMapper.toCard(cardDto, userId);
        Card newCard = cardRepository.save(cardData);
        return CardMapper.toResponseCardDto(newCard);
    }

    @Override
    public ResponseCardDto getById(Long cardId) throws SQLException {
        Card newCard = cardRepository.findById(cardId);
        return CardMapper.toResponseCardDto(newCard);
    }

    @Override
    public void like(Long cardId, Long userId) throws SQLException {
        Card card = cardRepository.findById(cardId);
        User user = userRepository.findById(userId);
        cardRepository.likeCard(cardId, userId);
    }

    @Override
    public void dislike(Long cardId, Long userId) throws SQLException {
        Card card = cardRepository.findById(cardId);
        User user = userRepository.findById(userId);
        cardRepository.dislikeCard(cardId, userId);
    }

}