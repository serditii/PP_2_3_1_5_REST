package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.util.UserNotFoundExeption;
import ru.kata.spring.boot_security.demo.util.UserNotUpdatedException;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin
@RestController
@RequestMapping("/")
public class AdminController {

    private final UserService userService;
    private final UserValidator userValidator;

    public AdminController(UserService userServise, UserValidator userValidator) {
        this.userService = userServise;
        this.userValidator = userValidator;
    }

    @GetMapping("/users")
    public List<UserDTO> showAll() {
        return userService.getListUsers().stream().map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> create(@RequestBody @Valid User user, BindingResult bindingResult
    ) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotCreatedException(errMsg.toString());
        }
        if (user.getRole().equals("ROLE_ADMIN")) {
            user.setRoles(Collections.singleton(new Role(2)));
        } else {
            user.setRoles(Collections.singleton(new Role(1)));
        }
        System.out.println(user);
        userService.addUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    public UserDTO show(@PathVariable() long id) {

        return convertToUserDTO(userService.showUser(id));
    }

    @GetMapping("/users1/{email}")
    public UserDTO showEmail(@PathVariable() String email) {

        return convertToUserDTO(userService.findByUsername(email));
    }

    @GetMapping("/user/show")
    public UserDTO showUser(Principal principal) {
        return convertToUserDTO(userService.showUser(userService
                .findByUsername(principal.getName()).getId()));
    }

    @PatchMapping("/update")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid User user,
                                             BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            StringBuilder errMsg = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for (FieldError error : errors) {
                errMsg.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";");
            }
            throw new UserNotUpdatedException(errMsg.toString());
        }
        if (user.getRole().equals("ROLE_ADMIN")) {
            user.setRoles(Collections.singleton(new Role(2)));
        } else user.setRoles(Collections.singleton(new Role(1)));
        userService.updateUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable() long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotFoundExeption e) {

        UserErrorResponse response = new UserErrorResponse(
                "User with this id wasn't found!", System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException(UserNotCreatedException e) {

        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(), System.currentTimeMillis()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    private UserDTO convertToUserDTO(User user) {
        UserDTO userDTO = new UserDTO();
        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setLastname(user.getLastname());
        userDTO.setAge(user.getAge());
        userDTO.setEmail(user.getEmail());
        userDTO.setPassword(user.getPassword());
        StringBuilder role = new StringBuilder();
        for (Role r : user.getRoles()) {
            role.append(r.getRole())
                    .append(" ");
        }
        userDTO.setRole(role.toString());
        return userDTO;
    }
}


