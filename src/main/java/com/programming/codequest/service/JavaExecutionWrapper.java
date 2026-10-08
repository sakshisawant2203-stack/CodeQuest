package com.programming.codequest.service;

import com.programming.codequest.Parser.InputField;
import com.programming.codequest.Parser.OutputField;
import com.programming.codequest.entity.Problem;
import com.programming.codequest.entity.Submission;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JavaExecutionWrapper {

    public String generate(Submission submission) {

        Problem problem = submission.getProblem();

        String userCode = submission.getSourceCode();

        StringBuilder builder = new StringBuilder();

        // =====================================================
        // IMPORTS
        // =====================================================

        builder.append("import java.util.*;\n\n");


        // =====================================================
        // USER'S SOLUTION
        // =====================================================

        builder.append(userCode);

        builder.append("\n\n");


        // =====================================================
        // MAIN CLASS
        // This part is hidden from the user.
        // =====================================================

        builder.append("public class Main {\n\n");

        builder.append("    public static void main(String[] args) {\n\n");

        builder.append("        Scanner sc = new Scanner(System.in);\n\n");


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
        // CREATE SOLUTION OBJECT
        // =====================================================

        builder.append(
                "        Solution solution = new Solution();\n\n"
        );


        // =====================================================
        // CALL USER FUNCTION
        // =====================================================

        builder.append("        ");

        OutputField outputField =
                problem.getOutputField();

        if (outputField != null &&
                !"void".equalsIgnoreCase(outputField.getType())) {

            builder.append(
                    getJavaType(outputField.getType())
            );

            builder.append(" result = ");
        }

        builder.append(
                "solution."
        );

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
                    "String[]".equals(outputType)) {

                builder.append(
                        "        System.out.println(Arrays.toString(result));\n"
                );

            } else if ("void".equalsIgnoreCase(outputType)) {

                // Nothing to print.

            } else {

                builder.append(
                        "        System.out.println(result);\n"
                );
            }
        }


        // =====================================================
        // CLOSE MAIN
        // =====================================================

        builder.append("    }\n");

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
                        "        int "
                );

                builder.append(name);

                builder.append(
                        " = sc.nextInt();\n\n"
                );

                break;


            case "long":

                builder.append(
                        "        long "
                );

                builder.append(name);

                builder.append(
                        " = sc.nextLong();\n\n"
                );

                break;


            case "double":

                builder.append(
                        "        double "
                );

                builder.append(name);

                builder.append(
                        " = sc.nextDouble();\n\n"
                );

                break;


            case "boolean":

                builder.append(
                        "        boolean "
                );

                builder.append(name);

                builder.append(
                        " = sc.nextBoolean();\n\n"
                );

                break;


            case "string":

                builder.append(
                        "        String "
                );

                builder.append(name);

                builder.append(
                        " = sc.next();\n\n"
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
                "        int[] "
        );

        builder.append(name);

        builder.append(" = new int[");

        builder.append(lengthVariable);

        builder.append("];\n\n");


        builder.append(
                "        for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "            "
        );

        builder.append(name);

        builder.append("[i] = sc.nextInt();\n");

        builder.append(
                "        }\n\n"
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
                "        long[] "
        );

        builder.append(name);

        builder.append(" = new long[");

        builder.append(lengthVariable);

        builder.append("];\n\n");


        builder.append(
                "        for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "            "
        );

        builder.append(name);

        builder.append("[i] = sc.nextLong();\n");

        builder.append(
                "        }\n\n"
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
                "        double[] "
        );

        builder.append(name);

        builder.append(" = new double[");

        builder.append(lengthVariable);

        builder.append("];\n\n");


        builder.append(
                "        for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "            "
        );

        builder.append(name);

        builder.append("[i] = sc.nextDouble();\n");

        builder.append(
                "        }\n\n"
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
                "        String[] "
        );

        builder.append(name);

        builder.append(" = new String[");

        builder.append(lengthVariable);

        builder.append("];\n\n");


        builder.append(
                "        for (int i = 0; i < "
        );

        builder.append(lengthVariable);

        builder.append("; i++) {\n");

        builder.append(
                "            "
        );

        builder.append(name);

        builder.append("[i] = sc.next();\n");

        builder.append(
                "        }\n\n"
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

    private String getJavaType(String type) {

        return switch (type) {

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
}
