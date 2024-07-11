package titov.services.user;

import titov.requestDto.RequestUpdatedUserDto;
import titov.requestDto.RequestUserDto;
import titov.responseDto.ResponseUserDto;
import java.sql.SQLException;

public interface UserService {
    ResponseUserDto create(RequestUserDto userDto) throws SQLException;
    ResponseUserDto getById(Long userId) throws SQLException;
    ResponseUserDto update(RequestUpdatedUserDto userDto, Long userId)  throws SQLException ;
    void delete(Long id) throws SQLException;
}