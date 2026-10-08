package com.programming.codequest.controller;

import com.programming.codequest.dto.Judge0SubmissionResponse;
import com.programming.codequest.service.SubmissionCallbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submissions")
public class SubmissionCallbackController {

    @Autowired
    private SubmissionCallbackService callbackService;

    @PutMapping("/callback")
    public ResponseEntity<?> handleCallback(
            @RequestBody Judge0SubmissionResponse response) {

        callbackService.processCallback(response);

        return ResponseEntity.ok().build();
    }
}