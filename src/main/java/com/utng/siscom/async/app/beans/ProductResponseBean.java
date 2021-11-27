package com.utng.siscom.async.app.beans;

import com.utng.siscom.async.app.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@NoArgsConstructor
@ToString
public class ProductResponseBean {
    private int status;
    private Product product;
}
