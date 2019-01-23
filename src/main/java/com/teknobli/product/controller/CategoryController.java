package com.teknobli.product.controller;

import com.teknobli.product.dto.CategoryDTO;
import com.teknobli.product.entity.Category;
import com.teknobli.product.entity.Product;
import com.teknobli.product.service.CategoryService;
import com.teknobli.product.service.ProductService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<Category> add(@RequestBody CategoryDTO categoryDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        Category categoryCreated = categoryService.save(category);
        return new ResponseEntity<>(categoryCreated,HttpStatus.CREATED);

    }

    @RequestMapping(value = "/select/{categoryId}",method=RequestMethod.GET)
    public Category getSingleCategory(@PathVariable("categoryId") String categoryId){
        return categoryService.findOne(categoryId);
    }

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public List<Category> getAllCategory(){
        return categoryService.findAll();
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseEntity<Category> updateCategory(@RequestBody CategoryDTO categoryDTO){
        Category category = new Category();
        BeanUtils.copyProperties(categoryDTO, category);
        Category categoryUpdated = categoryService.update(category);
        return new ResponseEntity<>(categoryUpdated,HttpStatus.CREATED);
    }

    @RequestMapping(value = "/delete/{categoryId}",method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteCategory(@PathVariable("categoryId") String categoryId){
        categoryService.delete(categoryId);
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }

    @RequestMapping(value = "/getproducts/{categoryId}",method = RequestMethod.GET)
    public List<Product> getProductByCategory(@PathVariable("categoryId") String categoryId){
        return productService.productByCategory(categoryId);
    }



}
