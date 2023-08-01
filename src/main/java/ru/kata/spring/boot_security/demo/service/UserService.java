package ru.kata.spring.boot_security.demo.service;

import org.springframework.validation.BindingResult;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;

import javax.validation.Valid;
import java.util.List;

public interface UserService {

    void addUser(@Valid User user, BindingResult bindingResult);

    void deleteUser(long id);

    User showUser(long id);

    void updateUser(User updateUser, BindingResult bindingResult);

    List<UserDTO> getListUsers();

    User findByUsername(String username);

    UserDTO convertToUserDTO(User showUser);
}
