package com.programming.codequest.generator;

import com.programming.codequest.entity.DefaultCode;
import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import com.programming.codequest.Parser.ProblemDefinition;
import org.springframework.stereotype.Component;

@Component
public class JavaScriptGenerator {

    public DefaultCode generate(ProblemDefinition definition) {

        StringBuilder builder = new StringBuilder();

        builder.append("function ");

        builder.append(definition.getFunctionName());

        builder.append("(");

        for (int i = 0; i < definition.getInputFields().size(); i++) {

            InputField field = definition.getInputFields().get(i);

            builder.append(field.getName());

            if (i != definition.getInputFields().size() - 1) {
                builder.append(", ");
            }
        }

        builder.append(") {\n\n");

        builder.append("    // Write your code here\n\n");

        builder.append(getDefaultReturn(definition.getOutputField()));

        builder.append("\n");

        builder.append("}\n");

        return DefaultCode.builder()
                .language("javascript")
                .code(builder.toString())
                .build();
    }

    private String getDefaultReturn(OutputField field) {

        if (field == null) {
            return "";
        }

        return switch (field.getType()) {

            case "int" -> "    return 0;";

            case "long" -> "    return 0;";

            case "double" -> "    return 0.0;";

            case "boolean" -> "    return false;";

            case "string" -> "    return \"\";";

            case "int[]" -> "    return [];";

            case "long[]" -> "    return [];";

            case "double[]" -> "    return [];";

            case "string[]" -> "    return [];";

            default -> "";
        };
    }

}
