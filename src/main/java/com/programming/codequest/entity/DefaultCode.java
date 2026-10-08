package com.programming.codequest.entity;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Document(collection = "default_codes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DefaultCode {

    @Id
    private String id;

    private String language;

    private String code;

    @JsonIgnore   // Don't send the problem inside every defaultCode when converting the Problem to JSON."
    @DBRef
    private Problem problem;
}