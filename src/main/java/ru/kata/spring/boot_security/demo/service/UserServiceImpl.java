package ru.kata.spring.boot_security.demo.service;

import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kata.spring.boot_security.demo.dto.UserDTO;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.repositories.UsersRepository;
import ru.kata.spring.boot_security.demo.util.UserErrorResponse;
import ru.kata.spring.boot_security.demo.util.UserNotCreatedException;
import ru.kata.spring.boot_security.demo.util.UserNotFoundExeption;
import ru.kata.spring.boot_security.demo.util.UserNotUpdatedException;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.persistence.EntityManager;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UsersRepository usersRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private EntityManager entityManager;
    private final UserValidator userValidator;

    @Lazy
    public UserServiceImpl(UsersRepository usersRepository, BCryptPasswordEncoder bCryptPasswordEncoder, EntityManager entityManager, UserValidator userValidator) {
        this.usersRepository = usersRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.entityManager = entityManager;
        this.userValidator = userValidator;
    }

    @Transactional
    @Override
    public void addUser(User user, BindingResult bindingResult) {
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
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setUsername(user.getEmail());
        usersRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(long id) {
        if (usersRepository.findById(id).isPresent()) {
            usersRepository.deleteById(id);
        }else throw new UserNotFoundExeption();
    }

    @Transactional(readOnly = true)
    @Override
    public User showUser(long id) {
        Optional<User> userFromDb = usersRepository.findById(id);
        return userFromDb.orElseThrow(UserNotFoundExeption::new);
    }

    @Transactional
    @Override
    public void updateUser(User updateUser, BindingResult bindingResult) {
        userValidator.validate(updateUser, bindingResult);
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
        if (updateUser.getRole().equals("ROLE_ADMIN")) {
            updateUser.setRoles(Collections.singleton(new Role(2)));
        } else updateUser.setRoles(Collections.singleton(new Role(1)));
        if (updateUser.getPassword().isEmpty()) {
            updateUser.setPassword(showUser(updateUser.getId()).getPassword());
        } else updateUser.setPassword(bCryptPasswordEncoder.encode(updateUser.getPassword()));
        updateUser.setUsername(updateUser.getEmail());
        usersRepository.saveAndFlush(updateUser);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDTO> getListUsers() {
        return usersRepository.findAll().stream().map(this::convertToUserDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = usersRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String
                    .format("User '%s' not found", username));
        }
        return user;
    }

    @Override
    @Transactional
    public User findByUsername(String username) {
        return usersRepository.findByUsername(username);
    }

    @Override
    public UserDTO convertToUserDTO(User user) {
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
}

