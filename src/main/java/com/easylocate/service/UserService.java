package com.easylocate.service;

import com.easylocate.model.User;
import com.easylocate.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<User> findUserById(@NonNull Long id) {
        return userRepository.findById(id);
    }

    public User saveUser(@javax.validation.Valid User user) {
        return userRepository.save(user);
    }
}
