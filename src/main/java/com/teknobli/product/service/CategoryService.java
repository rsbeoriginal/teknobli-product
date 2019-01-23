package com.teknobli.product.service;

import com.teknobli.product.entity.Category;

import java.util.List;

public interface CategoryService {

    public Category save(Category employee);
    public void delete(String employeeId);
    public List<Category> findAll();
    public Category findOne(String employeeId);
    public Category update(Category employee);

}
