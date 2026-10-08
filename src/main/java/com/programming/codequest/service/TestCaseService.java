package com.programming.codequest.service;

import com.programming.codequest.entity.TestCase;

import java.util.List;

public interface TestCaseService {

    void createTestCase(String problemId,TestCase testCase);

    List<TestCase> getAllTestCases();

    TestCase getTestCaseById(String id);

    void updateTestCase(TestCase testCase);

    void deleteTestCase(String id);
}