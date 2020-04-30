package com.zmeev.oauth2Demo.services;

import com.zmeev.oauth2Demo.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class AuthProvider implements AuthenticationProvider {

    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @Autowired
    public AuthProvider(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public Authentication authenticate(Authentication authentication) throws AuthenticationException
    {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userService.loadUserByUsername(username);

        if(user != null && (user.getName().equals(username) || user.getEmail().equals(username)))
        {
            if(!passwordEncoder.matches(password, user.getPassword()))
            {
                throw new BadCredentialsException("Wrong password");
            }

            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();

            return new UsernamePasswordAuthenticationToken(user, password, authorities);
        }
        else
            throw new BadCredentialsException("Username not found");
    }

    public boolean supports(Class<?> arg)
    {
        return true;
    }
}
