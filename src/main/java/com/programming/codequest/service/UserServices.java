package com.programming.codequest.service;

import com.programming.codequest.dto.LoginRequest;
import com.programming.codequest.dto.RegisterRequest;
import com.programming.codequest.entity.User;
import com.programming.codequest.repository.UserRepository;
import com.programming.codequest.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static ch.qos.logback.classic.spi.ThrowableProxyVO.build;

@Service  // add @Service here so it doesn't found null and spring will create object of this.
public class UserServices {

    @Autowired
    private UserRepository userRepository;  //
    // here what actually happen userServices called userRepository

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    // Userservice has business logic to Register New User.
    public void registerUser(RegisterRequest request) {

        // Check whether username already exists
        User existingUser = userRepository.findByUsername(request.getUsername());

        if (existingUser != null) {
            throw new RuntimeException("Username already exists");
        }

        // Convert DTO into Entity
        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                // Now registration stores a BCrypt hash instead of plain text.
                .role("USER")    // NEW
                .build();




        userRepository.save(user);
    }

    // Login User

  //  UserServices gives username + password to Spring Security
    // AuthenticationManager is responsible for authentication.
  // Spring Security checks the password
    public String loginUser(LoginRequest request) {
        authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken( // this method contain username password supplied by user.then paased it to authenticationmanager.
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        // Authentication succeeds → JWT is generated :code moves to following line
        return jwtUtil.generateToken(request.getUsername());
// so now Spring Security confirmed the user,generate a JWT for this user,
// JwtUtil creates something like:eyJhbGciOiJIUzI1NiJ9.. and returns to client.
        }

    // Find User By Username
    public User findByUsername(String request) {
        return userRepository.findByUsername(request);
    }
}

