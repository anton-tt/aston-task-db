package titov.services.user;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import titov.mappers.UserMapper;
import titov.models.User;
import titov.repositories.UserRepository;
import titov.requestDto.RequestUpdatedUserDto;
import titov.requestDto.RequestUserDto;
import titov.responseDto.ResponseUserDto;
import java.sql.SQLException;

@Data
@Slf4j
public class UserServiceImpl implements UserService {
    UserRepository userRepository = new UserRepository();

    @Override
    public ResponseUserDto create(RequestUserDto userDto) throws SQLException {
        User userData = UserMapper.toUser(userDto);
        User newUser = userRepository.save(userData);
        return UserMapper.toResponseUserDto(newUser);
    }

    @Override
    public ResponseUserDto getById(Long userId) throws SQLException {
        User newUser = userRepository.findById(userId);
        return UserMapper.toResponseUserDto(newUser);
    }

    @Override
    public ResponseUserDto update(RequestUpdatedUserDto userDto, Long userId) throws SQLException {
        User user = userRepository.findById(userId);
        User userData = UserMapper.toUpdatedUser(user, userDto);
        User updatedUser = userRepository.update(userData);
        return UserMapper.toResponseUserDto(updatedUser);
    }

    @Override
    public void delete(Long userId) throws SQLException {
        User user = userRepository.findById(userId);
        userRepository.remove(userId);
    }

}