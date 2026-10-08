

package com.programming.codequest.generator;

import com.programming.codequest.entity.DefaultCode;
import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import com.programming.codequest.Parser.ProblemDefinition;
import org.springframework.stereotype.Component;

@Component
public class PythonGenerator {

    public DefaultCode generate(ProblemDefinition definition) {

        StringBuilder builder = new StringBuilder();

        builder.append("class Solution:\n");

        builder.append("    def ");

        builder.append(definition.getFunctionName());

        builder.append("(self");

        for (InputField field : definition.getInputFields()) {

            builder.append(", ");
            builder.append(field.getName());

        }

        builder.append("):\n\n");

        builder.append("        # Write your code here\n\n");

        builder.append(getDefaultReturn(definition.getOutputField()));

        builder.append("\n");

        return DefaultCode.builder()
                .language("python")
                .code(builder.toString())
                .build();

    }

    private String getDefaultReturn(OutputField field) {

        if (field == null) {

            return "";

        }

        return switch (field.getType()) {

            case "int" -> "        return 0";

            case "long" -> "        return 0";

            case "double" -> "        return 0.0";

            case "boolean" -> "        return False";

            case "string" -> "        return \"\"";

            case "int[]" -> "        return []";

            case "long[]" -> "        return []";

            case "double[]" -> "        return []";

            case "string[]" -> "        return []";

            default -> "";

        };

    }

}