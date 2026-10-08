package com.programming.codequest.service;

import com.programming.codequest.dto.Judge0SubmissionResponse;
import com.programming.codequest.dto.SubmissionRequest;
import com.programming.codequest.entity.Problem;
import com.programming.codequest.entity.Submission;
import com.programming.codequest.entity.TestCase;
import com.programming.codequest.repository.ProblemRepository;
import com.programming.codequest.repository.SubmissionRepository;
import com.programming.codequest.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionServiceImpl implements SubmissionServices {

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private CodeExecutionService codeExecutionService;

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Override
    public void createSubmission(Submission submission) {
        submissionRepository.save(submission);
    }

    @Override
    public List<Submission> getAllSubmissions() {
        return submissionRepository.findAll();
    }

    @Override
    public Submission getSubmissionById(String id) {
        return submissionRepository.findById(id).orElse(null);
    }

    @Override
    public void updateSubmission(Submission submission) {
        submissionRepository.save(submission);
    }

    @Override
    public void deleteSubmission(String id) {
        submissionRepository.deleteById(id);
    }

    @Override
   public Submission submitSolution(SubmissionRequest request) {

        // 1. Find the selected problem
        Problem problem = problemRepository.findById(request.getProblemId())
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        // 2. Get test cases belonging to that problem
        List<TestCase> testCases = problem.getTestCases();

        if (testCases == null || testCases.isEmpty()) {
            throw new RuntimeException("No test cases found for this problem");
        }

        // 3. Create submission
        Submission submission = new Submission();

        submission.setProblem(problem);
        submission.setTestCases(testCases);
        submission.setLanguage(request.getLanguage());
        submission.setSourceCode(request.getSourceCode());
        submission.setStatus("PENDING");

        // 4. Save submission
        submissionRepository.save(submission);

        // 5. Start code execution
        codeExecutionService.execute(submission);

        return submission;
    }

    @Override
    public void handleJudge0Callback(Judge0SubmissionResponse response) {

        TestCase testCase = testCaseRepository
                .findByJudge0TrackingId(response.getToken())
                .orElseThrow(() ->
                        new RuntimeException("Test case not found"));

        testCase.setStatus(
                response.getStatus() != null
                        ? response.getStatus().getDescription()
                        : null
        );

        testCase.setStdout(response.getStdout());

        testCase.setStderr(response.getStderr());

        testCase.setCompileOutput(response.getCompile_output());

        testCase.setMessage(response.getMessage());

        testCase.setTime(response.getTime());

        testCase.setMemory(
                response.getMemory() != null
                        ? response.getMemory().doubleValue()
                        : null
        );

        // 3. Save testcase
        testCaseRepository.save(testCase);

        // 4. Find the parent submission
        Submission submission = submissionRepository
                .findById(testCase.getSubmissionId())
                .orElseThrow(() ->
                        new RuntimeException("Submission not found"));

        // 5. Get all testcases of this submission
        List<TestCase> testCases = submission.getTestCases();

        // 6. Check whether all testcases have finished
        boolean allCompleted = testCases.stream()
                .allMatch(tc ->
                        tc.getStatus() != null
                                && !tc.getStatus().equals("PENDING")
                );

        // If some testcases are still running,
        // keep submission status as RUNNING
        if (!allCompleted) {
            submission.setStatus("RUNNING");
            submissionRepository.save(submission);
            return;
        }

        // 7. All testcases completed.
        // Check whether every testcase was accepted.
        boolean allAccepted = testCases.stream()
                .allMatch(tc ->
                        "Accepted".equals(tc.getStatus())
                );

        // 8. Set final submission status
        submission.setStatus(
                allAccepted
                        ? "ACCEPTED"
                        : "REJECTED"
        );

        submissionRepository.save(submission);
    }
}