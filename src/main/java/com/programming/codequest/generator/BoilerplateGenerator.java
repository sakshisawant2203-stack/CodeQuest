package com.programming.codequest.generator;

import com.programming.codequest.entity.DefaultCode;
import com.programming.codequest.Parser.ProblemDefinition;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BoilerplateGenerator {


    private final CppGenerator cppGenerator;


    private final JavaGenerator javaGenerator;

    private final PythonGenerator pythonGenerator;

    private final JavaScriptGenerator javaScriptGenerator;

    public BoilerplateGenerator(CppGenerator cppGenerator,
                                JavaGenerator javaGenerator,
                                PythonGenerator pythonGenerator,
                                JavaScriptGenerator javaScriptGenerator) {

        this.cppGenerator = cppGenerator;
        this.javaGenerator = javaGenerator;
        this.pythonGenerator = pythonGenerator;
        this.javaScriptGenerator = javaScriptGenerator;
    }
    public List<DefaultCode> generate(ProblemDefinition definition) {

        List<DefaultCode> boilerplates = new ArrayList<>();

        boilerplates.add(cppGenerator.generate(definition));

        boilerplates.add(javaGenerator.generate(definition));

        boilerplates.add(pythonGenerator.generate(definition));

        boilerplates.add(javaScriptGenerator.generate(definition));

        return boilerplates;
    }

}