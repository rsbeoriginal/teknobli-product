package com.teknobli.product.service.impl;

import com.teknobli.product.entity.Category;
import com.teknobli.product.repository.CategoryRepository;
import com.teknobli.product.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceimpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public Category save(Category category) {
        if(category == null)
            return null;
        return categoryRepository.save(category);
    }

    @Override
    public void delete(String categoryId) {

        categoryRepository.delete(categoryId);

    }

    @Override
    public List<Category> findAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category findOne(String categoryId) {
        return categoryRepository.findOne(categoryId);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }
}
