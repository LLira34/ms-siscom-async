package com.utng.siscom.async.app.web.router.handler;

import com.utng.siscom.async.app.domain.Product;
import com.utng.siscom.async.app.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
@Slf4j
public class ProductHandler {

    @Autowired
    private ProductService productService;

    public Mono<ServerResponse> findAll(ServerRequest serverRequest) {
        return ServerResponse
            .ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(productService.findAll(), Product.class);
    }

    public Mono<ServerResponse> findById(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return productService
            .findById(id)
            .flatMap(product -> ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product))
            .onErrorResume(throwable -> {
                log.error(throwable.getMessage());
                return ServerResponse.notFound().build();
            })
            .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> insert(ServerRequest serverRequest) {
        Mono<Product> data = serverRequest.bodyToMono(Product.class);
        return data
            .flatMap(product -> productService.insert(product))
            .flatMap(product -> ServerResponse
                .created(URI.create("/api/v2/products".concat(product.getId())))
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(product));
    }

    public Mono<ServerResponse> update(ServerRequest serverRequest) {
        Mono<Product> data = serverRequest.bodyToMono(Product.class);
        String id = serverRequest.pathVariable("id");
        return data
            .flatMap(product -> productService.update(product, id))
            .flatMap(product -> {
                if (product.getStatus() == 200) {
                    return ServerResponse
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(product.getProduct());
                }
                return ServerResponse
                    .created(URI.create("/api/v2/products".concat(product.getProduct().getId())))
                    .contentType(MediaType.APPLICATION_JSON)
                    .bodyValue(product.getProduct());
            });
    }

    public Mono<ServerResponse> delete(ServerRequest serverRequest) {
        String id = serverRequest.pathVariable("id");
        return productService
            .delete(id)
            .flatMap(unused -> ServerResponse.noContent().build());
    }
}
