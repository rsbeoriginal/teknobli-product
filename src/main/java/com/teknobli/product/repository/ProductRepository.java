package com.teknobli.product.repository;

import com.teknobli.product.entity.Category;
import com.teknobli.product.entity.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product,String> {



    List<Product> findByCategory(Category category);

}
