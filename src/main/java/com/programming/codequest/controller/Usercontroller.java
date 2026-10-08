package com.programming.codequest.controller;

import com.programming.codequest.dto.LoginRequest;
import com.programming.codequest.dto.RegisterRequest;
import com.programming.codequest.entity.User;
import com.programming.codequest.service.UserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// client sends post request to the register endpoint which is handled by the usercontroller
@RequestMapping("/Users")
public class Usercontroller {
// Usercontroller just handles http request.

    @Autowired
    private UserServices userService;

    @PostMapping("/register")  //  @PostMapping beacuse we are adding something
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {

        userService.registerUser(request);
        return new ResponseEntity<>("User Registered Successfully", HttpStatus.CREATED);
    }

    // Login User
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        String token = userService.loginUser(request);

        return new ResponseEntity<>(token, HttpStatus.OK);
    }
}



