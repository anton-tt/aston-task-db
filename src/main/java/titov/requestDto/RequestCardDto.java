package titov.requestDto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class RequestCardDto {
    @NonNull
    private String name;
    @NonNull
    private String link;
}