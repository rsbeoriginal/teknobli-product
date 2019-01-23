package com.teknobli.product.controller;

import com.teknobli.product.dto.CategoryDTO;
import com.teknobli.product.dto.HomeProductDTO;
import com.teknobli.product.dto.ProductDTO;
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
@RequestMapping("/product")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<Product> addProduct(@RequestBody ProductDTO productDTO){
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);
        Product productCreated = productService.save(product);
        if(productCreated != null) {
            return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(productCreated,HttpStatus.EXPECTATION_FAILED);
        }

    }

    @RequestMapping(value = "/select/{productId}",method=RequestMethod.GET)
    public Product getSingleProduct(@PathVariable("productId") String productId){
        return productService.findOne(productId);
    }

    @RequestMapping(value = "/select",method = RequestMethod.GET)
    public List<Product> getAllProduct(){
        return productService.findAll();
    }

    @RequestMapping(value = "/update",method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@RequestBody ProductDTO productDTO){
        Product product = new Product();
        BeanUtils.copyProperties(productDTO, product);

        Product productCreated = productService.update(product);
        if(productCreated != null)
            return new ResponseEntity<>(productCreated,HttpStatus.CREATED);
        else
            return new ResponseEntity<>(productCreated,HttpStatus.EXPECTATION_FAILED);

    }

    @RequestMapping(value = "/delete/{productId}",method = RequestMethod.DELETE)
    public ResponseEntity<Boolean> deleteProduct(@PathVariable("productId") String productId){
        productService.delete(productId);
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }

    @RequestMapping(value = "/home",method = RequestMethod.GET)
    public List<HomeProductDTO> getHomeProducts(){
        return productService.productOnHome();
    }

    @RequestMapping(value = "/updaterating/{productId}/{newRating:.+}",method = RequestMethod.PUT)
    public ResponseEntity<Boolean> updateRating(@PathVariable("productId") String productId,@PathVariable("newRating") Double newRating){
        productService.updateProduct(productId,newRating);
        System.out.println(newRating);
        return new ResponseEntity<>(Boolean.TRUE,HttpStatus.OK);
    }

}
