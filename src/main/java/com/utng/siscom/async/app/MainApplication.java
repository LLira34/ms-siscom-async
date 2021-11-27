package com.utng.siscom.async.app;

import com.utng.siscom.async.app.domain.Category;
import com.utng.siscom.async.app.domain.Product;
import com.utng.siscom.async.app.repository.CategoryRepository;
import com.utng.siscom.async.app.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@SpringBootApplication
public class MainApplication implements CommandLineRunner {
    private static final Logger log = LoggerFactory.getLogger(MainApplication.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ReactiveMongoTemplate reactiveMongoTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }

    @Override
    public void run(String... args) {
        reactiveMongoTemplate.dropCollection("products").subscribe();
        reactiveMongoTemplate.dropCollection("categories").subscribe();

        Mono.just(new Category("Electronic"))
            .flatMap(category -> categoryRepository.save(category))
            .flatMapMany(category -> Flux.just(new Product("TV", 123.0, category.getId()),
                    new Product("Laptop", 321.0, category.getId()),
                    new Product("Camara", 1250.00, category.getId()),
                    new Product("Mobil", 4500.23, category.getId()))
                .flatMap(product -> {
                    product.setCreatedAt(LocalDateTime.now());
                    return productRepository.insert(product);
                }))
            .subscribe(product -> log.info(product.getId()), throwable -> log.error(throwable.getMessage()));
    }
}
