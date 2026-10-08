package com.programming.codequest.controller;

import com.programming.codequest.dto.Judge0SubmissionResponse;
import com.programming.codequest.dto.SubmissionRequest;
import com.programming.codequest.entity.Submission;

import com.programming.codequest.service.SubmissionServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionServices submissionService;

    @PostMapping
    public ResponseEntity<?> createSubmission(@RequestBody Submission submission) {

        submissionService.createSubmission(submission);

        return new ResponseEntity<>(submission, HttpStatus.CREATED);
    }

    @GetMapping("/fetch")
    public ResponseEntity<?> getAllSubmissions() {

        List<Submission> submissions = submissionService.getAllSubmissions();

        if (submissions != null && !submissions.isEmpty()) {
            return new ResponseEntity<>(submissions, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSubmission(@PathVariable String id) {

        Submission submission = submissionService.getSubmissionById(id);

        if (submission != null) {
            return new ResponseEntity<>(submission, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubmission(@PathVariable String id,
                                              @RequestBody Submission submission) {

        Submission oldSubmission = submissionService.getSubmissionById(id);

        if (oldSubmission != null) {

            oldSubmission.setLanguage(
                    submission.getLanguage() != null
                            ? submission.getLanguage()
                            : oldSubmission.getLanguage());

            oldSubmission.setSourceCode(
                    submission.getSourceCode() != null
                            ? submission.getSourceCode()
                            : oldSubmission.getSourceCode());

            oldSubmission.setStatus(
                    submission.getStatus() != null
                            ? submission.getStatus()
                            : oldSubmission.getStatus());

            oldSubmission.setTime(
                    submission.getTime() != null
                            ? submission.getTime()
                            : oldSubmission.getTime());

            oldSubmission.setMemory(
                    submission.getMemory() != null
                            ? submission.getMemory()
                            : oldSubmission.getMemory());

            submissionService.updateSubmission(oldSubmission);

            return new ResponseEntity<>(oldSubmission, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubmission(@PathVariable String id) {

        submissionService.deleteSubmission(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/submit")
    public ResponseEntity<?> submitSolution(
            @RequestBody SubmissionRequest request) {

        try {

            Submission submission =
                    submissionService.submitSolution(request);

            return new ResponseEntity<>(
                    submission,
                    HttpStatus.CREATED
            );

        } catch (Exception e) {

            e.printStackTrace();

            return new ResponseEntity<>(
                    e.getMessage(),
                    HttpStatus.BAD_REQUEST
            );
        }
    }

    @PostMapping("/callback")
    public ResponseEntity<?> judge0Callback(
            @RequestBody Judge0SubmissionResponse response) {

        submissionService.handleJudge0Callback(response);

        return ResponseEntity.ok().build();
    }
}

