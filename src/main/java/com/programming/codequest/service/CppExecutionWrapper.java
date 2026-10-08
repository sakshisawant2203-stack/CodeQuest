package com.programming.codequest.service;

import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import com.programming.codequest.entity.Problem;
import com.programming.codequest.entity.Submission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CppExecutionWrapper {

    public String generate(Submission submission) {

        Problem problem = submission.getProblem();

        String userCode = submission.getSourceCode();

        StringBuilder builder = new StringBuilder();


        // =====================================================
        // IMPORTS
        // =====================================================

        builder.append("#include <bits/stdc++.h>\n");
        builder.append("using namespace std;\n\n");


        // =====================================================
        // USER'S SOLUTION
        // =====================================================

        builder.append(userCode);

        builder.append("\n\n");


        // =====================================================
        // HIDDEN MAIN
        // =====================================================

        builder.append("int main() {\n\n");


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

        builder.append("    ");

        OutputField outputField =
                problem.getOutputField();

        if (outputField != null &&
                !"void".equalsIgnoreCase(outputField.getType())) {

            builder.append(
                    getCppType(outputField.getType())
            );

            builder.append(" result = ");
        }

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

        builder.append(");\n\n");


        // =====================================================
        // PRINT RESULT
        // =====================================================

        if (outputField != null) {

            String outputType =
                    outputField.getType();

            if ("int[]".equals(outputType) ||
                    "long[]".equals(outputType) ||
                    "double[]".equals(outputType) ||
                    "string[]".equals(outputType)) {

                builder.append(
                        "    for (size_t i = 0; i < result.size(); i++) {\n"
                );

                builder.append(
                        "        if (i > 0) cout << \" \";\n"
                );

                builder.append(
                        "        cout << result[i];\n"
                );

                builder.append(
                        "    }\n"
                );

                builder.append(
                        "    cout << endl;\n"
                );

            } else if ("boolean".equals(outputType)) {

                builder.append(
                        "    cout << boolalpha << result << endl;\n"
                );

            } else if ("void".equalsIgnoreCase(outputType)) {

                // Nothing to print.

            } else {

                builder.append(
                        "    cout << result << endl;\n"
                );
            }
        }


        // =====================================================
        // CLOSE MAIN
        // =====================================================

        builder.append("}\n");

        return builder.toString();
    }


    // =========================================================
    // INPUT READER
    // =========================================================

    private void appendInputReader(
            StringBuilder builder,
            InputField field,
            List<InputField> allFields) {

        String type = field.getType();

        String name = field.getName();


        switch (type) {

            case "int":

                builder.append(
                        "    int "
                );

                builder.append(name);

                builder.append(";\n");

                builder.append(
                        "    cin >> "
                );

                builder.append(name);

                builder.append(";\n\n");

                break;


            case "long":

                builder.append(
                        "    long long "
                );

                builder.append(name);

                builder.append(";\n");

                builder.append(
                        "    cin >> "
                );

                builder.append(name);

                builder.append(";\n\n");

                break;


            case "double":

                builder.append(
                        "    double "
                );

                builder.append(name);

                builder.append(";\n");

                builder.append(
                        "    cin >> "
                );

                builder.append(name);

                builder.append(";\n\n");

                break;


            case "boolean":

                builder.append(
                        "    bool "
                );

                builder.append(name);

                builder.append(";\n");

                builder.append(
                        "    cin >> "
                );

                builder.append(name);

                builder.append(";\n\n");

                break;


            case "string":

                builder.append(
                        "    string "
                );

                builder.append(name);

                builder.append(";\n");

                builder.append(
                        "    cin >> "
                );

                builder.append(name);

                builder.append(";\n\n");

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
                        "Unsupported input type: " + type
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
                "    vector<int> "
        );

        builder.append(name);

        builder.append("(");

        builder.append(lengthVariable);

        builder.append(");\n\n");


        builder.append(
                "    for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "        cin >> "
        );

        builder.append(name);

        builder.append("[i];\n");

        builder.append(
                "    }\n\n"
        );
    }


    // =========================================================
    // LONG ARRAY
    // =========================================================

    private void appendLongArrayReader(
            StringBuilder builder,
            String name,
            List<InputField> fields) {

        String lengthVariable =
                findPreviousLengthVariable(
                        name,
                        fields
                );

        builder.append(
                "    vector<long long> "
        );

        builder.append(name);

        builder.append("(");

        builder.append(lengthVariable);

        builder.append(");\n\n");


        builder.append(
                "    for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "        cin >> "
        );

        builder.append(name);

        builder.append("[i];\n");

        builder.append(
                "    }\n\n"
        );
    }


    // =========================================================
    // DOUBLE ARRAY
    // =========================================================

    private void appendDoubleArrayReader(
            StringBuilder builder,
            String name,
            List<InputField> fields) {

        String lengthVariable =
                findPreviousLengthVariable(
                        name,
                        fields
                );

        builder.append(
                "    vector<double> "
        );

        builder.append(name);

        builder.append("(");

        builder.append(lengthVariable);

        builder.append(");\n\n");


        builder.append(
                "    for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "        cin >> "
        );

        builder.append(name);

        builder.append("[i];\n");

        builder.append(
                "    }\n\n"
        );
    }


    // =========================================================
    // STRING ARRAY
    // =========================================================

    private void appendStringArrayReader(
            StringBuilder builder,
            String name,
            List<InputField> fields) {

        String lengthVariable =
                findPreviousLengthVariable(
                        name,
                        fields
                );

        builder.append(
                "    vector<string> "
        );

        builder.append(name);

        builder.append("(");

        builder.append(lengthVariable);

        builder.append(");\n\n");


        builder.append(
                "    for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "        cin >> "
        );

        builder.append(name);

        builder.append("[i];\n");

        builder.append(
                "    }\n\n"
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


    // =========================================================
    // OUTPUT TYPE
    // =========================================================

    private String getCppType(String type) {

        return switch (type) {

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