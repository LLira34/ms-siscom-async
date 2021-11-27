package com.utng.siscom.async.app.service.impl;

import com.utng.siscom.async.app.beans.ProductResponseBean;
import com.utng.siscom.async.app.common.exceptions.SiscomException;
import com.utng.siscom.async.app.domain.Product;
import com.utng.siscom.async.app.repository.ProductRepository;
import com.utng.siscom.async.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Flux<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Mono<Product> findById(String id) {
        return productRepository
            .existsById(id)
            .flatMap(aBoolean -> {
                if (Boolean.FALSE.equals(aBoolean)) {
                    throw new SiscomException("No se encontro el recurso");
                }
                return productRepository.findById(id);
            });
    }

    @Override
    public Mono<Product> insert(Product data) {
        if (data.getCreatedAt() == null) {
            data.setCreatedAt(LocalDateTime.now());
        }
        return productRepository.save(data);
    }

    @Override
    public Mono<ProductResponseBean> update(Product data, String id) {
        return productRepository
            .existsById(id)
            .flatMap(aBoolean -> {
                if (Boolean.FALSE.equals(aBoolean)) {
                    Product doc = new Product();
                    doc.setName(data.getName());
                    doc.setPrice(data.getPrice());
                    doc.setCategoryId(data.getCategoryId());
                    doc.setCreatedAt(LocalDateTime.now());
                    log.info("Producto creado");
                    return productRepository
                        .save(doc)
                        .map(product -> {
                            ProductResponseBean productResponseBean = new ProductResponseBean();
                            productResponseBean.setStatus(201);
                            productResponseBean.setProduct(product);
                            return productResponseBean;
                        });
                }
                return productRepository
                    .findById(id)
                    .flatMap(product -> {
                        product.setName(data.getName());
                        product.setPrice(data.getPrice());
                        product.setCategoryId(data.getCategoryId());
                        log.info("Producto actualizado");
                        return productRepository
                            .save(product)
                            .map(p -> {
                                ProductResponseBean productResponseBean = new ProductResponseBean();
                                productResponseBean.setStatus(200);
                                productResponseBean.setProduct(p);
                                return productResponseBean;
                            });
                    });
            });
    }

    @Override
    public Mono<Void> delete(String id) {
        return productRepository
            .findById(id)
            .flatMap(product -> productRepository.delete(product));
    }
}
