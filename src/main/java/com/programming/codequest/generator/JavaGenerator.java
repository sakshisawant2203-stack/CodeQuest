package com.programming.codequest.generator;

import com.programming.codequest.entity.DefaultCode;
import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import com.programming.codequest.Parser.ProblemDefinition;
import org.springframework.stereotype.Component;

@Component
public class JavaGenerator {

    public DefaultCode generate(ProblemDefinition definition) {

        StringBuilder builder = new StringBuilder();

        builder.append("class Solution {\n\n");

        builder.append("    public ");

        builder.append(getJavaReturnType(definition.getOutputField()));

        builder.append(" ");

        builder.append(definition.getFunctionName());

        builder.append("(");

        for (int i = 0; i < definition.getInputFields().size(); i++) {

            InputField field = definition.getInputFields().get(i);

            builder.append(getJavaType(field));

            builder.append(" ");

            builder.append(field.getName());

            if (i != definition.getInputFields().size() - 1) {

                builder.append(", ");

            }

        }

        builder.append(") {\n\n");

        builder.append("        // Write your code here\n\n");

        builder.append(getDefaultReturn(definition.getOutputField()));

        builder.append("\n");

        builder.append("    }\n");

        builder.append("}\n");

        return DefaultCode.builder()
                .language("java")
                .code(builder.toString())
                .build();

    }

    private String getJavaType(InputField field) {

        return switch (field.getType()) {

            case "int" -> "int";

            case "long" -> "long";

            case "double" -> "double";

            case "boolean" -> "boolean";

            case "string" -> "String";

            case "int[]" -> "int[]";

            case "long[]" -> "long[]";

            case "double[]" -> "double[]";

            case "string[]" -> "String[]";

            default -> "Object";

        };

    }

    private String getJavaReturnType(OutputField field) {

        if (field == null) {

            return "void";

        }

        return switch (field.getType()) {

            case "int" -> "int";

            case "long" -> "long";

            case "double" -> "double";

            case "boolean" -> "boolean";

            case "string" -> "String";

            case "int[]" -> "int[]";

            case "long[]" -> "long[]";

            case "double[]" -> "double[]";

            case "string[]" -> "String[]";

            default -> "void";

        };

    }

    private String getDefaultReturn(OutputField field) {

        if (field == null) {

            return "";

        }

        return switch (field.getType()) {

            case "int" -> "return 0;";

            case "long" -> "return 0L;";

            case "double" -> "return 0.0;";

            case "boolean" -> "return false;";

            case "string" -> "return \"\";";

            case "int[]" -> "return new int[]{};";

            case "long[]" -> "return new long[]{};";

            case "double[]" -> "return new double[]{};";

            case "string[]" -> "return new String[]{};";

            default -> "";

        };

    }

}
