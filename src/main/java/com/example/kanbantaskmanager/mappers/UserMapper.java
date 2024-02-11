package com.example.kanbantaskmanager.mappers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.UpdateUserDto;
import com.example.kanbantaskmanager.dtos.UserDto;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Role;
import com.example.kanbantaskmanager.models.User;
import com.example.kanbantaskmanager.repositories.BoardRepository;

@Service
public class UserMapper {

    @Autowired
    BoardRepository boardRepository;

    public UserDto convertToDto(User user) {
        UserDto newUser = new UserDto();

        List<Long> boardIds = user.getBoards().stream().map(Board::getId).collect(Collectors.toList());
        List<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        newUser.setBoards(boardIds);
        newUser.setRoles(roleIds);
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setEmail(user.getEmail());

        return newUser;
    }

    public User convertToEntity(UserDto userDto) {
        User newUser = new User();

        newUser.setId(userDto.getId());
        newUser.setUsername(userDto.getUsername());
        newUser.setEmail(userDto.getEmail());

        return newUser;
    }

    public UpdateUserDto convertToUpdateDto(User user) {
        UpdateUserDto newUser = new UpdateUserDto();

        newUser.setEmail(user.getEmail());
        newUser.setPassword(user.getPassword());
        newUser.setUsername(user.getUsername());

        return newUser;
    }
}
