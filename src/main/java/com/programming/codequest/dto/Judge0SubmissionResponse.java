package com.programming.codequest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Judge0SubmissionResponse {

    private String stdout;

    private String time;

    private Integer memory;

    private String stderr;

    private String token;

    private String compile_output;

    private String message;

    private Judge0Status status;
}