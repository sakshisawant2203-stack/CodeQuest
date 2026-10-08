package com.programming.codequest.service;

import com.programming.codequest.dto.Judge0SubmissionResponse;
import com.programming.codequest.entity.Submission;
import com.programming.codequest.entity.TestCase;

public interface Judge0Service {

    Judge0SubmissionResponse submit(
            Submission submission,
            TestCase testCase
    );
}