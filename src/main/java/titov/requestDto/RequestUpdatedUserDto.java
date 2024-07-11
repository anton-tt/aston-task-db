package titov.requestDto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class RequestUpdatedUserDto {
    private String name;
    private String about;
    private String avatar;
    private String email;
}
