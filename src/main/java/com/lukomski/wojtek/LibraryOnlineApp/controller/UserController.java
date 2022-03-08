package com.lukomski.wojtek.LibraryOnlineApp.controller;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.UserAlreadyExistException;
import com.lukomski.wojtek.LibraryOnlineApp.model.User;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.UserRepository;
import com.lukomski.wojtek.LibraryOnlineApp.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(value = "/user/add", consumes = "application/json")
    ResponseEntity<User> add(@RequestBody User user) {
        Optional<User> user1 = Optional.of(userService.add(user));
        return ResponseEntity.of(user1);
    }

    @GetMapping("/user/all")
    List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/user/login/{login}")
    User getUserByLogin(@PathVariable String login) {
        return userService.getUserByLogin(login);
    }

    @GetMapping("/user/{userId}")
    ResponseEntity<User> getUserBy(@PathVariable Integer userId) {
        return userService.getUserById(userId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    ResponseEntity<String> handlerExceptions() {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("User already exist, please choose different login");
    }
}
