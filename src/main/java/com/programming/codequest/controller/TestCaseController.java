package com.programming.codequest.controller;


import com.programming.codequest.entity.TestCase;
import com.programming.codequest.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {

    @Autowired
    private TestCaseService testCaseService;

    @PostMapping("/problem/{problemId}")
    public ResponseEntity<?> createTestCase( @PathVariable String problemId,@RequestBody TestCase testCase) {
// @PathVariable- identifies the Problem for which I am creating the test case,
// @RequestBody contains the actual test case data such as input, expected output, and hidden status."
        testCaseService.createTestCase(problemId,testCase);

        return new ResponseEntity<>(testCase, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getAllTestCases() {

        List<TestCase> testCases = testCaseService.getAllTestCases();

        if (!testCases.isEmpty()) {
            return new ResponseEntity<>(testCases, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTestCase(@PathVariable String id) {

        TestCase testCase = testCaseService.getTestCaseById(id);

        if (testCase != null) {
            return new ResponseEntity<>(testCase, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTestCase(@PathVariable String id,
                                            @RequestBody TestCase testCase) {

        TestCase oldTestCase = testCaseService.getTestCaseById(id);

        if (oldTestCase != null) {

            oldTestCase.setInput(testCase.getInput());
            oldTestCase.setOutput(testCase.getOutput());
            oldTestCase.setHidden(testCase.isHidden());

            testCaseService.updateTestCase(oldTestCase);

            return new ResponseEntity<>(oldTestCase, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTestCase(@PathVariable String id) {

        testCaseService.deleteTestCase(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}