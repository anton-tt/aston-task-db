package titov.models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.ArrayList;

@Data
@Builder
@Slf4j
public class Card {
    private Long id;
    private String name;
    private String link;
    private LocalDate created;
    private Long ownerId;
    private ArrayList<Long> likes;
}