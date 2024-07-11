package titov.responseDto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@Slf4j
public class ResponseCardDto {
    private String name;
    private String link;
    private LocalDate created;
    private List<ResponseUserDto> likes;
}
