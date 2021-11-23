package com.utng.siscom.async.app.web.router.handler;

import com.utng.siscom.async.app.domain.Product;
import com.utng.siscom.async.app.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;

@Component
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
}