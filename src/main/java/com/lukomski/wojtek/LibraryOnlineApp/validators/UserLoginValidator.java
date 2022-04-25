package com.lukomski.wojtek.LibraryOnlineApp.validators;

import com.lukomski.wojtek.LibraryOnlineApp.model.User;
import com.lukomski.wojtek.LibraryOnlineApp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserLoginValidator {
    private UserRepository userRepository;

    @Autowired
    public UserLoginValidator(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public boolean validate(User user) {
        User getUser = userRepository.findByLogin(user.getLogin());
        return getUser != null;
    }
}
