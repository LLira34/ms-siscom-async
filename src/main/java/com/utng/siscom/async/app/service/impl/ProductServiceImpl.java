package com.utng.siscom.async.app.service.impl;

import com.utng.siscom.async.app.domain.Product;
import com.utng.siscom.async.app.repository.ProductRepository;
import com.utng.siscom.async.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(String id) {
        return productRepository.findById(id);
    }

    @Override
    public Mono<Product> insert(Product data) {
        if (data.getCreatedAt() == null) {
            data.setCreatedAt(LocalDateTime.now());
        }
        return productRepository.save(data);
    }

    @Override
    public Mono<Product> update(Product data, String id) {
        return this.findById(id)
                .flatMap(product -> {
                    product.setName(data.getName());
                    product.setPrice(data.getPrice());
                    return productRepository.save(product);
                });
    }

    @Override
    public Mono<Void> delete(String id) {
        return productRepository.deleteById(id);
    }
}
