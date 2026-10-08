package com.programming.codequest.service;


import com.programming.codequest.dto.Judge0SubmissionResponse;
import com.programming.codequest.dto.SubmissionRequest;
import com.programming.codequest.entity.Submission;

import java.util.List;

public interface SubmissionServices {

    void createSubmission(Submission submission);

    List<Submission> getAllSubmissions();

    Submission getSubmissionById(String id);

    void updateSubmission(Submission submission);

    void deleteSubmission(String id);

    Submission submitSolution(SubmissionRequest request);

    void handleJudge0Callback(Judge0SubmissionResponse response);
}
