package com.utng.siscom.async.app.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "categories")
public class Category {
    @Id
    private String id;
    private String name;

    public Category(String name) {
        this.name = name;
    }
}
