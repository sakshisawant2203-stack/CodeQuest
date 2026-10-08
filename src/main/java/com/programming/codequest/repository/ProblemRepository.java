package com.programming.codequest.repository;

import com.programming.codequest.entity.Problem;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public interface ProblemRepository extends MongoRepository<Problem, String> {

    Optional<Problem> findBySlug(String slug);

}