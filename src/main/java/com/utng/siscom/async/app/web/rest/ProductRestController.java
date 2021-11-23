package com.utng.siscom.async.app.web.rest;

import com.utng.siscom.async.app.domain.Product;
import com.utng.siscom.async.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@RestController
@RequestMapping("/api/v1")
public class ProductRestController {

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public Mono<ResponseEntity<Flux<Product>>> findAll() {
        return Mono
                .just(ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(productService.findAll()));
    }

    @GetMapping("/products/{id}")
    public Mono<ResponseEntity<Product>> findById(@PathVariable String id) {
        return productService
                .findById(id)
                .map(product -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/products")
    public Mono<ResponseEntity<Product>> insert(@RequestBody Product body) {
        return productService
                .insert(body)
                .map(product -> ResponseEntity.created(URI.create("/api/v1/products".concat(product.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product));
    }

    @PutMapping("/products/{id}")
    public Mono<ResponseEntity<Product>> update(@RequestBody Product body, @PathVariable String id) {
        return productService
                .update(body, id)
                .map(product -> ResponseEntity.created(URI.create("/api/v1/products".concat(product.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(product));
    }
}
