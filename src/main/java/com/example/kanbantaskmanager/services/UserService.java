package com.example.kanbantaskmanager.services;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.kanbantaskmanager.dtos.UpdateUserDto;
import com.example.kanbantaskmanager.dtos.UpdatePasswordDto;
import com.example.kanbantaskmanager.dtos.UserDto;
import com.example.kanbantaskmanager.mappers.UserMapper;
import com.example.kanbantaskmanager.models.Board;
import com.example.kanbantaskmanager.models.Role;
import com.example.kanbantaskmanager.models.User;
import com.example.kanbantaskmanager.repositories.BoardRepository;
import com.example.kanbantaskmanager.repositories.RoleRepository;
import com.example.kanbantaskmanager.repositories.UserRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private BoardRepository boardRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User getUserById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User with id " + userId + " not found."));
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public void deleteById(Long userId) {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User with id: " + userId + " not found"));
        List<Long> boardIds = userToDelete.getBoards().stream().map(Board::getId).collect(Collectors.toList());
        Set<Board> boardsWithAccessTo = boardRepository.findAllById(boardIds).stream().collect(Collectors.toSet());
        Set<Board> usersBoards = userToDelete.getBoards();
        boardsWithAccessTo.removeAll(usersBoards);

        for (Board board : boardsWithAccessTo) {
            Set<User> users = board.getUsers();
            users.remove(userToDelete);
            boardRepository.save(board);
        }
        userRepository.deleteById(userId);

    }

    public UserDto addUserRoles(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        Set<Role> userRoles = user.getRoles();
        Role newRole = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));
        userRoles.add(newRole);
        user.setRoles(userRoles);
        userRepository.save(user);
        return userMapper.convertToDto(user);
    }

    public UserDto removeAdminRole(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found with id: " + userId));
        List<Long> roleIds = user.getRoles().stream().map(Role::getId).collect(Collectors.toList());
        if (roleIds != null && !roleIds.isEmpty()) {
            roleIds.removeIf(roleId -> roleId.equals(2L));
            Set<Role> newRoles = new HashSet<>(roleRepository.findAllById(roleIds));
            user.setRoles(newRoles);
            userRepository.save(user);
        }
        return userMapper.convertToDto(user);
    }

    public UpdateUserDto updateUserInformation(Long userId, UpdateUserDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with id: " + userId));
        if (!passwordEncoder.matches(userDto.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("The provided password is incorrect.");
        }
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        userRepository.save(user);
        return userMapper.convertToUpdateDto(user);
    }

    public UpdateUserDto updateUserPassword(Long userId, UpdatePasswordDto userDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("No user found with id: " + userId));

        if (!passwordEncoder.matches(userDto.getUserDto().getPassword(),
                user.getPassword())) {
            throw new IllegalArgumentException("The provided password is incorrect.");
        }
        user.setPassword(passwordEncoder.encode(userDto.getNewPassword()));
        userRepository.save(user);

        return userMapper.convertToUpdateDto(user);
    }

    public UserDto getUserDetails(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user found with id: " + userId));
        return userMapper.convertToDto(user);
    }

    public UserDto abandonBoardAccess(Long boardId, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("No user found with id: " + userId));
        Set<Board> userBoards = user.getBoards();
        Board boardToAbandon = boardRepository.findById(boardId)
                .orElseThrow(() -> new EntityNotFoundException("No board found with id: " + boardId));
        // remove board from your set of boards
        if (boardToAbandon.getOwner().getId() != userId) {
            userBoards.remove(boardToAbandon);
            user.setBoards(userBoards);
            userRepository.save(user);

            Set<User> boardsUsers = boardToAbandon.getUsers();
            // remove yourself from boards users set
            boardsUsers.remove(user);
            boardToAbandon.setUsers(boardsUsers);
            boardRepository.save(boardToAbandon);
        }

        return userMapper.convertToDto(user);
    }

    @Transactional
    public void deleteAccount(UpdateUserDto userDto, Long userId) {
        User userToDelete = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("There is no user with provided id."));
        if (!passwordEncoder.matches(userDto.getPassword(), userToDelete.getPassword())) {
            throw new IllegalArgumentException("The provided password is incorrect.");
        }
        List<Long> boardIds = userToDelete.getBoards().stream().map(Board::getId).collect(Collectors.toList());
        Set<Board> boardsWithAccessTo = boardRepository.findAllById(boardIds).stream().collect(Collectors.toSet());
        Set<Board> usersBoards = userToDelete.getBoards();
        boardsWithAccessTo.removeAll(usersBoards);

        for (Board board : boardsWithAccessTo) {
            Set<User> users = board.getUsers();
            users.remove(userToDelete);
            boardRepository.save(board);
        }

        userRepository.delete(userToDelete);
    }
}
