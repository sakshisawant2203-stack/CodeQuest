package com.programming.codequest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubmissionRequest {

    private String problemId;

    private String language;

    private String sourceCode;
}
