package com.programming.codequest.reader;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class StructureFileReader {

    public String readStructure(String slug) {

        try {

            ClassPathResource resource =
                    new ClassPathResource("problems/" + slug + "/Structure.md");

            byte[] bytes = resource.getInputStream().readAllBytes();

            return new String(bytes, StandardCharsets.UTF_8);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Unable to read Structure.md for problem : " + slug
            );
        }
    }
}
