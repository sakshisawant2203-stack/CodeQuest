package com.programming.codequest.service;

import com.programming.codequest.entity.Problem;

import java.util.List;

// I use the interface to define the service operations separately from their implementation. ProblemServiceImpl implements those operations.
public interface ProblemService {

    void createProblem(Problem problem);

    List<Problem> getAllProblems();

    Problem getProblem(String slug);

    Problem getProblemById(String id);

    void updateProblem(Problem problem);

    void deleteProblem(String id);
}