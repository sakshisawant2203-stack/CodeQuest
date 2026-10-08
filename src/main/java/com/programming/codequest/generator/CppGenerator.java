package com.programming.codequest.generator;

import com.programming.codequest.entity.DefaultCode;
import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import com.programming.codequest.Parser.ProblemDefinition;
import org.springframework.stereotype.Component;

@Component
public class CppGenerator {

    public DefaultCode generate(ProblemDefinition definition) {

        StringBuilder builder = new StringBuilder();

        builder.append("#include <bits/stdc++.h>\n");
        builder.append("using namespace std;\n\n");

        builder.append(getCppReturnType(definition.getOutputField()));

        builder.append(" ");

        builder.append(definition.getFunctionName());

        builder.append("(");

        for (int i = 0; i < definition.getInputFields().size(); i++) {

            InputField field = definition.getInputFields().get(i);

            builder.append(getCppType(field));

            builder.append(" ");

            builder.append(field.getName());

            if (i != definition.getInputFields().size() - 1) {

                builder.append(", ");

            }

        }

        builder.append(")");

        builder.append(" {\n\n");

        builder.append("    // Write your code here\n\n");

        builder.append("}\n");

        return DefaultCode.builder()
                .language("cpp")
                .code(builder.toString())
                .build();

    }

    private String getCppType(InputField field) {

        return switch (field.getType()) {

            case "int" -> "int";

            case "long" -> "long long";

            case "double" -> "double";

            case "boolean" -> "bool";

            case "string" -> "string";

            case "int[]" -> "vector<int>";

            case "long[]" -> "vector<long long>";

            case "double[]" -> "vector<double>";

            case "string[]" -> "vector<string>";

            default -> "auto";

        };

    }

    private String getCppReturnType(OutputField field) {

        if (field == null) {

            return "void";

        }

        return switch (field.getType()) {

            case "int" -> "int";

            case "long" -> "long long";

            case "double" -> "double";

            case "boolean" -> "bool";

            case "string" -> "string";

            case "int[]" -> "vector<int>";

            case "long[]" -> "vector<long long>";

            case "double[]" -> "vector<double>";

            case "string[]" -> "vector<string>";

            default -> "void";

        };

    }

}