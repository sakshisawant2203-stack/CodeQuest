package com.programming.codequest.Parser;

import com.programming.codequest.Parser.ProblemDefinition;
import com.programming.codequest.entity.Problem;
import org.springframework.stereotype.Component;

@Component
public class ProblemDefinitionParser {


    public ProblemDefinition parse(Problem problem) {


        ProblemDefinition definition = new ProblemDefinition();

        definition.setFunctionName(problem.getFunctionName());

        definition.setInputFields(problem.getInputFields());

        definition.setOutputField(problem.getOutputField());

        System.out.println("Definition InputFields = " + definition.getInputFields());

        return definition;
    }

}
// ProblemDefinition is only used during 3boilerplate generation.