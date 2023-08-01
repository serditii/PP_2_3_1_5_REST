package ru.kata.spring.boot_security.demo.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;


    public AdminController(UserService userServise) {
        this.userService = userServise;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> showAll() {
        return ResponseEntity.ok(userService.getListUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserDTO> show(@PathVariable() long id) {
        return ResponseEntity.ok(userService.convertToUserDTO(userService.showUser(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<UserDTO> showUser(Principal principal) {
        return ResponseEntity.ok(userService.convertToUserDTO(userService.showUser(userService
                .findByUsername(principal.getName()).getId())));
    }

    @PostMapping("/users")
    public ResponseEntity<HttpStatus> create(@RequestBody User user, BindingResult bindingResult
    ) {
        userService.addUser(user, bindingResult);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/users")
    public ResponseEntity<HttpStatus> update(@RequestBody @Valid User user,
                                             BindingResult bindingResult) {
        userService.updateUser(user, bindingResult);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<HttpStatus> delete(@PathVariable() long id) {
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}


