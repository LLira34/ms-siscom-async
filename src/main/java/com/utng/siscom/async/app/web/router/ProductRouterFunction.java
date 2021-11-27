package com.utng.siscom.async.app.web.router;

import com.utng.siscom.async.app.service.ProductService;
import com.utng.siscom.async.app.web.router.handler.ProductHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class ProductRouterFunction {

    @Autowired
    private ProductService productService;

    @Bean
    public RouterFunction<ServerResponse> routes(ProductHandler productHandler) {
        return route(GET("/api/v2/products"), productHandler::findAll)
            .andRoute(GET("/api/v2/products/{id}"), productHandler::findById)
            .andRoute(POST("/api/v2/products"), productHandler::insert)
            .andRoute(PUT("/api/v2/products/{id}"), productHandler::update);
    }
}
