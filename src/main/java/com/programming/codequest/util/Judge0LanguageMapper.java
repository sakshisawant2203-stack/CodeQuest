package com.programming.codequest.util;

public class Judge0LanguageMapper {

    public static Integer getLanguageId(String language) {

        return switch (language.toUpperCase()) {

            case "JAVA" -> 62;

            case "CPP" -> 54;

            case "PYTHON" -> 71;

            case "JAVASCRIPT" -> 63;

            default -> throw new RuntimeException("Unsupported Language");
        };
    }
}

/* Judge0 does not understand: JAVA
CPP
PYTHON
JAVASCRIPT
It understands numeric language IDs.

 */