package titov.mappers;

import lombok.experimental.UtilityClass;
import titov.models.Card;
import titov.requestDto.RequestCardDto;
import titov.responseDto.ResponseCardDto;
import java.time.LocalDate;

@UtilityClass
public class CardMapper {
    public Card toCard(RequestCardDto requestCardDto, Long ownerId) {
        return Card.builder()
                .name(requestCardDto.getName())
                .link(requestCardDto.getLink())
                .created(LocalDate.now())
                .ownerId(ownerId)
                .build();
    }

    public Card toCard(Long id, String name, String link, LocalDate created, Long ownerId) {
        return Card.builder()
                .id(id)
                .name(name)
                .link(link)
                .created(created)
                .ownerId(ownerId)
                .build();
    }

    public ResponseCardDto toResponseCardDto(Card card) {
        return ResponseCardDto.builder()
                .name(card.getName())
                .link(card.getLink())
                .created(card.getCreated())
                .build();
    }

}