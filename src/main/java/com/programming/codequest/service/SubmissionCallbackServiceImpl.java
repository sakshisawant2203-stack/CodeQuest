package com.programming.codequest.service;

import com.programming.codequest.dto.Judge0SubmissionResponse;
import com.programming.codequest.entity.Submission;
import com.programming.codequest.entity.TestCase;
import com.programming.codequest.repository.SubmissionRepository;
import com.programming.codequest.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionCallbackServiceImpl
        implements SubmissionCallbackService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Override
    public void processCallback(
            Judge0SubmissionResponse response) {

        String token = response.getToken();

        System.out.println(
                "Callback received from Judge0. Token: "
                        + token
        );

        TestCase testCase =
                testCaseRepository
                        .findByJudge0TrackingId(token)
                        .orElse(null);

        if (testCase == null) {

            System.out.println(
                    "TestCase not found for token: "
                            + token
            );

            return;
        }

        testCase.setStatus(
                response.getStatus().getDescription()
        );

        testCase.setStdout(response.getStdout());

        testCase.setStderr(response.getStderr());

        testCase.setCompileOutput(
                response.getCompile_output()
        );

        testCase.setMessage(
                response.getMessage()
        );

        testCase.setTime(
                response.getTime()
        );

        if (response.getMemory() != null) {

            testCase.setMemory(
                    response.getMemory().doubleValue()
            );
        }

        testCaseRepository.save(testCase);

        updateSubmission(testCase);
    }

    private void updateSubmission(
            TestCase completedTestCase) {

        Submission submission =
                submissionRepository
                        .findById(
                                completedTestCase.getSubmissionId()
                        )
                        .orElse(null);

        if (submission == null) {
            return;
        }

        List<TestCase> testCases =
                submission.getTestCases();

        boolean allCompleted =
                testCases.stream()
                        .allMatch(testCase ->
                                testCase.getStatus() != null
                                        &&
                                        !testCase.getStatus()
                                                .equals("PENDING")
                        );

        if (!allCompleted) {
            return;
        }

        boolean allAccepted =
                testCases.stream()
                        .allMatch(testCase ->
                                "Accepted".equals(
                                        testCase.getStatus()
                                )
                        );

        submission.setStatus(
                allAccepted
                        ? "ACCEPTED"
                        : "REJECTED"
        );

        submissionRepository.save(submission);
    }
}