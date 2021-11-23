package com.utng.siscom.async.app.service;

import com.utng.siscom.async.app.domain.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> findAll();

    Mono<Product> findById(String id);

    Mono<Product> insert(Product data);

    Mono<Product> update(Product data, String id);

    Mono<Void> delete(String id);
}
