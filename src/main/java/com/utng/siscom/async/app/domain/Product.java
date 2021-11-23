package com.utng.siscom.async.app.domain;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(collection = "products")
public class Product {
    @Id
    private String id;
    private String name;
    private Double price;
    @Field("created_at")
    private LocalDateTime createdAt;
    @Field("category_id")
    private String categoryId;

    public Product(String name, Double price) {
        this.name = name;
        this.price = price;
    }

    public Product(String name, Double price, String categoryId) {
        this(name, price);
        this.categoryId = categoryId;
    }
}
