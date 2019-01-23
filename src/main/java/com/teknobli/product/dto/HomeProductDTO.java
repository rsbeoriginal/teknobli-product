package com.teknobli.product.dto;

import com.teknobli.product.entity.Category;
import com.teknobli.product.entity.Product;

import java.util.List;

public class HomeProductDTO {
    Category category;
    List<Product> productList;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }
}
