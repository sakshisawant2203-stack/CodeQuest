package com.programming.codequest.service;
import com.programming.codequest.Parser.ProblemDefinition;
import com.programming.codequest.entity.Problem;
import com.programming.codequest.repository.ProblemRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.programming.codequest.generator.BoilerplateGenerator;

import com.programming.codequest.Parser.ProblemDefinitionParser;

import java.time.LocalDateTime;
import java.util.List;

// This is where your main problem creation flow happens.


@Service
public class ProblemServiceImpl implements ProblemService {

    @Autowired
    private ProblemRepository problemRepository;


    @Autowired
    private ProblemDefinitionParser parser;

    @Autowired
    private BoilerplateGenerator boilerplateGenerator;

    @Override
    public void createProblem(Problem problem) {

        if (problem.getCreatedAt() == null) {
            problem.setCreatedAt(LocalDateTime.now());
        }

        problem.setUpdatedAt(LocalDateTime.now());
// The service first parses the problem into a structured ProblemDefinition.
// Then the BoilerplateGenerator generates default code for the supported languages. The generated default codes are added to the problem, and finally the ProblemRepository saves the problem in MongoDB
        ProblemDefinition definition = parser.parse(problem);

        problem.setDefaultCodes(
                boilerplateGenerator.generate(definition)
        );

        problemRepository.save(problem);
    }

    @Override
    public List<Problem> getAllProblems() {
        return problemRepository.findAll();
    }

    @Override
    public Problem getProblem(String slug) {
        return problemRepository.findBySlug(slug).orElse(null);
    }

    @Override
    public Problem getProblemById(String id) {
        return problemRepository.findById(id).orElse(null);
    }

    @Override
    public void updateProblem(Problem problem) {

        problem.setUpdatedAt(LocalDateTime.now());

        problemRepository.save(problem);
    }

    @Override
    public void deleteProblem(String id) {
        problemRepository.deleteById(id);
    }
}