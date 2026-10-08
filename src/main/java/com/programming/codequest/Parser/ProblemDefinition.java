package com.programming.codequest.Parser;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProblemDefinition {

    private String functionName;

    private List<InputField> inputFields;

    private OutputField outputField;
}