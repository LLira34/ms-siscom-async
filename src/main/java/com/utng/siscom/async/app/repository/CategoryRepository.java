package com.utng.siscom.async.app.repository;

import com.utng.siscom.async.app.domain.Category;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface CategoryRepository extends ReactiveMongoRepository<Category, String> {
}
