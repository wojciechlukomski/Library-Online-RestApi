package com.lukomski.wojtek.LibraryOnlineApp.services;

import com.lukomski.wojtek.LibraryOnlineApp.exceptions.UserAlreadyExistException;
import com.lukomski.wojtek.LibraryOnlineApp.model.User;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.UserRepository;
import com.lukomski.wojtek.LibraryOnlineApp.validators.UserLoginValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final UserLoginValidator userLoginValidator;

    @Autowired
    public UserService(UserRepository userRepository, UserLoginValidator userLoginValidator) {
        this.userRepository = userRepository;
        this.userLoginValidator = userLoginValidator;
    }
    public User add(User user) {
        userLoginValidator.validate(user);
        if(!userLoginValidator.validate(user)){
            return userRepository.save(user);
        }
        throw new UserAlreadyExistException("Please choose different login");
    }

    public void delete(User user) {
        userRepository.delete(user);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    public User getUserByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    public Optional<User> getUserById(Integer userId) {
        return userRepository.findById(userId);
    }

}
