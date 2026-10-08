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
    public class CodeExecutionServiceImpl implements CodeExecutionService {

        @Autowired
        private Judge0Service judge0Service;

        @Autowired
        private SubmissionRepository submissionRepository;

        @Autowired
        private TestCaseRepository testCaseRepository;

        @Override
        public void execute(Submission submission) {

            // 1. Mark submission as RUNNING
            submission.setStatus("RUNNING");
            submissionRepository.save(submission);

            // 2. Get all test cases of this problem
            List<TestCase> testCases = submission.getTestCases();

            int index = 0;

            // 3. Send every test case separately to Judge0
            for (TestCase testCase : testCases) {

                // Connect testcase with this submission
                testCase.setSubmissionId(
                        submission.getId()
                );

                // Store testcase position
                testCase.setIndex(index);

                // Initially testcase is waiting
                testCase.setStatus("PENDING");

                // Send source code + this testcase's
                // input/output to Judge0
                Judge0SubmissionResponse response =
                        judge0Service.submit(
                                submission,
                                testCase
                        );

                // Store Judge0 token against this testcase
                testCase.setJudge0TrackingId(
                        response.getToken()
                );

                // Save testcase in MongoDB
                testCaseRepository.save(testCase);

                index++;
            }
        }
    }