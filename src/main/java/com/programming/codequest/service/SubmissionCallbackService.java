package com.programming.codequest.service;

import com.programming.codequest.dto.Judge0SubmissionResponse;

public interface SubmissionCallbackService {

    void processCallback(
            Judge0SubmissionResponse response
    );
}