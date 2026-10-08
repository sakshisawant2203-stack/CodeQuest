package com.programming.codequest.controller;
import com.programming.codequest.entity.Problem;
import com.programming.codequest.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
public class ProblemController {

    @Autowired
    private ProblemService problemService;

    @GetMapping
    public ResponseEntity<?> getAllProblems() {
        List<Problem> problems = problemService.getAllProblems();

        if (problems != null && !problems.isEmpty()) {
            return new ResponseEntity<>(problems, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/{slug}")
    public ResponseEntity<?> getProblem(@PathVariable String slug) {

        Problem problem = problemService.getProblem(slug);

        if (problem != null) {
            return new ResponseEntity<>(problem, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // “The ProblemController handles HTTP requests related to problems. For example, it receives a problem creation request and passes the problem to ProblemService.
    @PostMapping
    public ResponseEntity<?> createProblem(@RequestBody Problem problem) {

        try {
            problemService.createProblem(problem);
            return new ResponseEntity<>(problem, HttpStatus.CREATED);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProblem(@PathVariable String id,
                                           @RequestBody Problem problem) {

        Problem oldProblem = problemService.getProblemById(id);

        if (oldProblem != null) {

            oldProblem.setTitle(problem.getTitle() != null && !problem.getTitle().equals("")
                    ? problem.getTitle()
                    : oldProblem.getTitle());

            oldProblem.setDescription(problem.getDescription() != null && !problem.getDescription().equals("")
                    ? problem.getDescription()
                    : oldProblem.getDescription());

            oldProblem.setDifficulty(problem.getDifficulty() != null
                    ? problem.getDifficulty()
                    : oldProblem.getDifficulty());

            oldProblem.setHidden(problem.isHidden());

            problemService.createProblem(oldProblem);

            return new ResponseEntity<>(oldProblem, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProblem(@PathVariable String id) {

        problemService.deleteProblem(id);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}