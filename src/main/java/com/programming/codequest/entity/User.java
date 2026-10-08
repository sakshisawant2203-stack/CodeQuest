package com.programming.codequest.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.*;
        import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

// @Data this annotation is equivalent to getter,setter,requiredArgsConstructor,toString,EqualsAndHashcode,value these all annotation
@Document(collection = "registration")  // document maps the class to the registration collection.
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    private String id; // u can also use String instead of ObjectId spring data mongodb will convert it automatically.

    @Indexed(unique = true) // indexing make our searching fast and unique make it unique for every user.
    @NonNull // this lombok's annotation use when we set something using setter if among field is null then null pointer exception will be thrown.
    private String username;

    @NonNull // this lombok's annotation use when we set something using setter if among field is null then null pointer exception will be thrown.
    private String password;

    private String role;  // new

    @DBRef // annotate by @DBRef means u are creating reference of submissions in user collection
    // means this submissions List will store reference of submissions which are created.
    private List<Submission> submissions;
}