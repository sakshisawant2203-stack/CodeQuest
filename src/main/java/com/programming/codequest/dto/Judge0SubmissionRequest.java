package com.programming.codequest.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Judge0SubmissionRequest {

    private String source_code;

    private Integer language_id;

    private String stdin;

    private String expected_output;

    private String callback_url;
}