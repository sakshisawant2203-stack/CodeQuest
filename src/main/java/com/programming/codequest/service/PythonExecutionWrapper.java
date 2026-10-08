package com.programming.codequest.service;

import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import com.programming.codequest.entity.Problem;
import com.programming.codequest.entity.Submission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PythonExecutionWrapper {

    public String generate(Submission submission) {

        Problem problem = submission.getProblem();

        String userCode = submission.getSourceCode();

        StringBuilder builder = new StringBuilder();


        // =====================================================
        // USER'S SOLUTION
        // =====================================================

        builder.append(userCode);

        builder.append("\n\n");


        // =====================================================
        // HIDDEN EXECUTION CODE
        // =====================================================

        builder.append("import sys\n\n");


        // =====================================================
        // READ INPUT
        // =====================================================

        List<InputField> inputFields =
                problem.getInputFields();

        for (InputField field : inputFields) {

            appendInputReader(
                    builder,
                    field,
                    inputFields
            );
        }


        // =====================================================
        // CALL USER FUNCTION
        // =====================================================

        builder.append("result = ");

        builder.append(
                problem.getFunctionName()
        );

        builder.append("(");


        for (int i = 0; i < inputFields.size(); i++) {

            builder.append(
                    inputFields.get(i).getName()
            );

            if (i < inputFields.size() - 1) {

                builder.append(", ");

            }
        }

        builder.append(")\n\n");


        // =====================================================
        // PRINT RESULT
        // =====================================================

        OutputField outputField =
                problem.getOutputField();

        if (outputField != null) {

            String outputType =
                    outputField.getType();

            if ("void".equalsIgnoreCase(outputType)) {

                // Nothing to print.

            } else if ("boolean".equals(outputType)) {

                builder.append(
                        "print(str(result).lower())\n"
                );

            } else if ("int[]".equals(outputType) ||
                    "long[]".equals(outputType) ||
                    "double[]".equals(outputType) ||
                    "string[]".equals(outputType)) {

                builder.append(
                        "print(*result)\n"
                );

            } else {

                builder.append(
                        "print(result)\n"
                );
            }
        }


        return builder.toString();
    }


    // =========================================================
    // INPUT READER
    // =========================================================

    private void appendInputReader(
            StringBuilder builder,
            InputField field,
            List<InputField> allFields) {

        String type =
                field.getType();

        String name =
                field.getName();


        switch (type) {

            case "int":

                builder.append(
                        name
                );

                builder.append(
                        " = int(input())\n\n"
                );

                break;


            case "long":

                builder.append(
                        name
                );

                builder.append(
                        " = int(input())\n\n"
                );

                break;


            case "double":

                builder.append(
                        name
                );

                builder.append(
                        " = float(input())\n\n"
                );

                break;


            case "boolean":

                builder.append(
                        name
                );

                builder.append(
                        " = input().lower() == 'true'\n\n"
                );

                break;


            case "string":

                builder.append(
                        name
                );

                builder.append(
                        " = input().strip()\n\n"
                );

                break;


            case "int[]":

                appendIntArrayReader(
                        builder,
                        name,
                        allFields
                );

                break;


            case "long[]":

                appendLongArrayReader(
                        builder,
                        name,
                        allFields
                );

                break;


            case "double[]":

                appendDoubleArrayReader(
                        builder,
                        name,
                        allFields
                );

                break;


            case "string[]":

                appendStringArrayReader(
                        builder,
                        name,
                        allFields
                );

                break;


            default:

                throw new RuntimeException(
                        "Unsupported input type: "
                                + type
                );
        }
    }


    // =========================================================
    // INT ARRAY
    // =========================================================

    private void appendIntArrayReader(
            StringBuilder builder,
            String name,
            List<InputField> fields) {

        String lengthVariable =
                findPreviousLengthVariable(
                        name,
                        fields
                );

        builder.append(
                name
        );

        builder.append(
                " = list(map(int, input().split()))\n\n"
        );
    }


    // =========================================================
    // LONG ARRAY
    // =========================================================

    private void appendLongArrayReader(
            StringBuilder builder,
            String name,
            List<InputField> fields) {

        builder.append(
                name
        );

        builder.append(
                " = list(map(int, input().split()))\n\n"
        );
    }


    // =========================================================
    // DOUBLE ARRAY
    // =========================================================

    private void appendDoubleArrayReader(
            StringBuilder builder,
            String name,
            List<InputField> fields) {

        builder.append(
                name
        );

        builder.append(
                " = list(map(float, input().split()))\n\n"
        );
    }


    // =========================================================
    // STRING ARRAY
    // =========================================================

    private void appendStringArrayReader(
            StringBuilder builder,
            String name,
            List<InputField> fields) {

        builder.append(
                name
        );

        builder.append(
                " = input().split()\n\n"
        );
    }


    // =========================================================
    // FIND ARRAY LENGTH
    // =========================================================

    private String findPreviousLengthVariable(
            String arrayName,
            List<InputField> fields) {

        for (int i = 0; i < fields.size(); i++) {

            InputField current =
                    fields.get(i);

            if (current.getName().equals(arrayName)) {

                for (int j = i - 1; j >= 0; j--) {

                    InputField previous =
                            fields.get(j);

                    if ("int".equals(previous.getType())) {

                        return previous.getName();
                    }
                }
            }
        }

        throw new RuntimeException(
                "No integer length field found for array: "
                        + arrayName
        );
    }
}