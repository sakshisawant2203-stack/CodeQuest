package com.programming.codequest.entity;

import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

// Problem stores only the problem data.

@Document(collection = "problems")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Problem {

    @Id
    private String id;

    private String title;

    private String slug;

    private String description;

    private String difficulty;

    private boolean hidden;

    private int solved;

    private String inputFormat;

    private String outputFormat;

    private String constraints;

    private String sampleInput;

    private String sampleOutput;

    private List<String> tags;

    private String functionName;

    private List<InputField> inputFields;

    private OutputField outputField;

    private List<DefaultCode> defaultCodes;

    @DBRef
    private List<TestCase> testCases;

    private List<Submission> submissions;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}