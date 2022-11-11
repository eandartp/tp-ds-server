package it.tacticalpeople.tpdsserver.mapper;

import it.tacticalpeople.tpdsserver.domain.User;
import it.tacticalpeople.tpdsserver.dto.UserDto;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class UserMapper implements Serializable {

    private static final long serialVersionUID = 1L;

    public User fromDtoToUser(UserDto userDto){
        User user = new User();
        user.setId(userDto.getId());
        user.setUsername(userDto.getUsername());
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setBusinessRole(userDto.getBusinessRole());
        user.setEmail(userDto.getEmail());
        return user;
    }

    public UserDto fromUserToDto(User user){
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setName(user.getName());
        userDto.setSurname(user.getSurname());
        userDto.setBusinessRole(user.getBusinessRole());
        userDto.setEmail(user.getEmail());
        return userDto;
    }
}
