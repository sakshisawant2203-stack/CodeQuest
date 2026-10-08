package com.programming.codequest.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "testcases")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestCase {

    @Id
    private String id;

    private String input;

    private String output;

    private boolean hidden;

    // Used to connect this testcase with its parent submission
    private String submissionId;

    // Position of this testcase in the problem
    private Integer index;

    // Judge0 token for this particular testcase
    private String judge0TrackingId;

    // Execution result
    private String status;

    private String stdout;

    private String stderr;

    private String compileOutput;

    private String message;

    private String time;

    private Double memory;
}