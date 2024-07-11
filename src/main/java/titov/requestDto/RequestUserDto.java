package titov.requestDto;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class RequestUserDto {
    @NonNull
    private String name;
    @NonNull
    private String about;
    @NonNull
    private String avatar;
    @NonNull
    private String email;
    @NonNull
    private String password;
}