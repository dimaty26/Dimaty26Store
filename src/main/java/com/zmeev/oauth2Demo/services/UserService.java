package com.zmeev.oauth2Demo.services;

import com.zmeev.oauth2Demo.entities.User;
import com.zmeev.oauth2Demo.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    private UserRepo userRepo;

    @Autowired
    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    public User loadUserByUsername(String username) throws UsernameNotFoundException
    {
        User userFindByName = userRepo.findByName(username);
        User userFindByEmail = userRepo.findByEmail(username);
        User userFindByGoogleUsername = userRepo.findByGoogleUsername(username);
        User userFindByGoogleName = userRepo.findByGoogleName(username);

        if (userFindByEmail != null) {
            return userFindByEmail;
        }

        if (userFindByName != null) {
            return userFindByName;
        }

        if (userFindByGoogleUsername != null) {
            return userFindByGoogleUsername;
        }

        if (userFindByGoogleName != null) {
            return userFindByGoogleName;
        }

        return null;
    }

    public User findByEmail(String email) {
        return userRepo.findByEmail(email);
    }

    public User findByConfirmationToken(String confirmationToken) {
        return userRepo.findByConfirmationToken(confirmationToken);
    }

    public void save(User user) {
        userRepo.save(user);
    }
}
