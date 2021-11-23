package com.utng.siscom.async.app.repository;

import com.utng.siscom.async.app.domain.Product;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ProductRepository extends ReactiveMongoRepository<Product, String> {
}
