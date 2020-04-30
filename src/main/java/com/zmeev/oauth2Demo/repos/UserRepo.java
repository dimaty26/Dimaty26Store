package com.zmeev.oauth2Demo.repos;

import com.zmeev.oauth2Demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
@Repository
public interface UserRepo extends JpaRepository<User, Long> {

    User findByName(String username);
    User findByEmail(String email);
    User findByConfirmationToken(String confirmationToken);
    User findByGoogleUsername(String googleUsername);
    User findByGoogleName(String googleName);
}
