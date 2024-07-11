package titov.models;

import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Data
@Builder
@Slf4j
public class User {
    private Long id;
    private String name;
    private String about;
    private String avatar;
    private String email;
    private String password;
}
