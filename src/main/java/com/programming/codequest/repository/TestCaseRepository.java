package com.programming.codequest.repository;

import com.programming.codequest.entity.TestCase;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface TestCaseRepository extends MongoRepository<TestCase, String> {
    Optional<TestCase> findByJudge0TrackingId(
            String judge0TrackingId
    );
}
