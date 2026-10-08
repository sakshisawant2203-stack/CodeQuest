package com.programming.codequest.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document(collection = "submissions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Submission {

    @Id
    private String id;

    @DBRef
    private Problem problem;

    @DBRef
    private List<TestCase> testCases;

    private String language;

    private String sourceCode;

    private String status;

    private Double time;

    private Double memory;

    // Judge0 token
    private String judge0Token;
}