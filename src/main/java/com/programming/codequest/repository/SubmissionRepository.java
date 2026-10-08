package com.programming.codequest.repository;

import com.programming.codequest.entity.Submission;

import org.springframework.data.mongodb.repository.MongoRepository;


public interface SubmissionRepository extends MongoRepository<Submission, String> {

}
