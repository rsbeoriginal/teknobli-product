package com.teknobli.product.service;

import com.teknobli.product.dto.HomeProductDTO;
import com.teknobli.product.entity.Category;
import com.teknobli.product.entity.Product;

import java.util.List;

public interface ProductService {

    public Product save(Product product);
    public void delete(String productId);
    public List<Product> findAll();
    public Product findOne(String productId);
    public Product update(Product product);
    public List<Product> productByCategory(String categoryId);
    public List<HomeProductDTO> productOnHome();
    public void updateProduct(String productId,Double rating);

}
