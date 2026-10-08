package com.programming.codequest.service;

import com.programming.codequest.entity.Problem;
import com.programming.codequest.entity.TestCase;
import com.programming.codequest.repository.ProblemRepository;
import com.programming.codequest.repository.TestCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestCaseServiceImpl implements TestCaseService {

    @Autowired
    private TestCaseRepository testCaseRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Override
    public void createTestCase(String problemId,TestCase testCase) {


        // Find the problem
        Problem problem = problemRepository.findById(problemId)
                .orElseThrow(() -> new RuntimeException("Problem not found"));

        // Save test case
        TestCase savedTestCase = testCaseRepository.save(testCase);

        // Add test case to problem
        if (problem.getTestCases() == null) {
            problem.setTestCases(new ArrayList<>());
        }

        problem.getTestCases().add(savedTestCase);

        // Save updated problem
        problemRepository.save(problem);
    }

    @Override
    public List<TestCase> getAllTestCases() {
        return testCaseRepository.findAll();
    }

    @Override
    public TestCase getTestCaseById(String id) {
        return testCaseRepository.findById(id).orElse(null);
    }

    @Override
    public void updateTestCase(TestCase testCase) {
        testCaseRepository.save(testCase);
    }

    @Override
    public void deleteTestCase(String id) {
        testCaseRepository.deleteById(id);
    }
}