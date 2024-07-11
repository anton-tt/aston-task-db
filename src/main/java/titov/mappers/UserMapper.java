package titov.mappers;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import titov.models.User;
import titov.requestDto.RequestUpdatedUserDto;
import titov.requestDto.RequestUserDto;
import titov.responseDto.ResponseUserDto;

@UtilityClass
@Slf4j
public class UserMapper {
    public User toUser(RequestUserDto requestUserDto) {
        return User.builder()
                .name(requestUserDto.getName())
                .about(requestUserDto.getAbout())
                .avatar(requestUserDto.getAvatar())
                .email(requestUserDto.getEmail())
                .password(requestUserDto.getPassword())
                .build();
    }

    public User toUser(Long id, String name, String about, String avatar, String email, String password) {
        return User.builder()
                .id(id)
                .name(name)
                .about(about)
                .avatar(avatar)
                .email(email)
                .password(password)
                .build();
    }

    public ResponseUserDto toResponseUserDto(User user) {
        return ResponseUserDto.builder()
                .name(user.getName())
                .about(user.getAbout())
                .avatar(user.getAvatar())
                .email(user.getEmail())
                .build();
    }

    public User toUpdatedUser(User user, RequestUpdatedUserDto userDto) {
        String userDtoName = userDto.getName();
        String userDtoAbout = user.getAbout();
        String userDtoAvatar = userDto.getAvatar();
        String userDtoEmail = userDto.getEmail();
        if (userDtoName != null && !userDtoName.isBlank()) {
            user.setName(userDtoName);
            log.info("Имя пользователя будет изменено на {}.", userDtoName);
        }

        if (userDtoAbout != null && !userDtoAbout.isBlank()) {
            user.setAbout(userDtoAbout);
            log.info("Деятельность пользователя будет изменена на {}.", userDtoAbout);
        }

        if (userDtoAvatar != null && !userDtoAvatar.isBlank()) {
            user.setAvatar(userDtoAvatar);
            log.info("Аватар пользователя будет изменён на {}.", userDtoAvatar);
        }

        if (userDtoEmail != null && !userDtoEmail.isBlank()) {
            if (user.getEmail().equals(userDtoEmail)) {
                log.info("Адрес новой электронной почты и адрес старой одинаковые: {}. " +
                        "Обновление данных не требуется.", userDtoEmail);
            } else {
                user.setEmail(userDtoEmail);
                log.info("Эл.почта пользователя изменена на {}.", userDtoEmail);
            }
        }
        return user;
    }

}