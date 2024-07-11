package titov.responseDto;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Slf4j
@Builder
public class ResponseUserDto {
    private String name;
    private String about;
    private String avatar;
    private String email;
}
