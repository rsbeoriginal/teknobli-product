package com.teknobli.product.service.impl;

import com.teknobli.product.dto.HomeProductDTO;
import com.teknobli.product.entity.Category;
import com.teknobli.product.entity.Product;
import com.teknobli.product.merchantmicroservice.Endpoints;
import com.teknobli.product.repository.ProductRepository;
import com.teknobli.product.service.CategoryService;
import com.teknobli.product.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryService categoryService;
    @Override
    public Product save(Product product) {
        Category category = product.getCategory();
        product.setCategory(categoryService.findOne(category.getCategoryId()));

        Product createdProduct = productRepository.save(product);

        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.postForObject( Endpoints.BASE_URL_SEARCH + Endpoints.ADD, createdProduct, Boolean.class);
        if(result){
            return createdProduct;
        }else {
            productRepository.delete(createdProduct.getProductId());
            return null;

        }


    }

    @Override
    public void delete(String productId) {

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete( Endpoints.BASE_URL_SEARCH + Endpoints.DELETE + productId);
        productRepository.delete(productId);

    }

    public Float getMinPrice(String productId){
        RestTemplate restTemplate = new RestTemplate();
        Float result = restTemplate.getForObject(Endpoints.BASE_URL + Endpoints.MINPRICE_URL + productId, Float.class);
        if(result != null){
            return result;
        }
        return  0f;
    }

    @Override
    public List<Product> findAll() {

        List<Product> productList = productRepository.findAll();
        for(Product product : productList){
                product.setPrice(getMinPrice(product.getProductId()));
                product.setStock(getTotalStock(product.getProductId()));
        }
        return productList;
    }

    @Override
    public Product findOne(String productId) {
        Product product = productRepository.findOne(productId);
        product.setPrice(getMinPrice(product.getProductId()));
        product.setStock(getTotalStock(product.getProductId()));
        return product;
    }

    private Integer getTotalStock(String productId) {
        RestTemplate restTemplate = new RestTemplate();
        Integer stock = restTemplate.getForObject(Endpoints.BASE_URL + Endpoints.GET_TOTAL_STOCK +productId,Integer.class);
        return stock;
    }

    @Override
    public Product update(Product product) {


        Product oldProduct = productRepository.findOne(product.getProductId());
        Category category = categoryService.findOne(oldProduct.getCategory().getCategoryId());
        System.out.println(category);
        product.setCategory(category);
        Product newProduct = productRepository.save(product);
        RestTemplate restTemplate = new RestTemplate();
        Boolean result = restTemplate.postForObject( Endpoints.BASE_URL_SEARCH + Endpoints.UPDATE, newProduct, Boolean.class);
        if(result){
            return newProduct;
        }else {
            productRepository.save(oldProduct);
            return null;

        }


    }

    @Override
    public List<Product> productByCategory(String categoryId) {
        Category category = categoryService.findOne(categoryId);
        List<Product> productList = productRepository.findByCategory(category);
        for(Product product : productList){
            product.setPrice(getMinPrice(product.getProductId()));
            product.setStock(getTotalStock(product.getProductId()));
            System.out.println(product.getProductName() +":" + product.getPrice());
        }
        return productList;
        //return productRepository.findAllBy(categoryId);
    }

    @Override
    public List<HomeProductDTO> productOnHome() {
        List<Category> categoryList = categoryService.findAll();
        List<HomeProductDTO> homeProductDTOList = new ArrayList<>();

        for(Category category : categoryList ){
            HomeProductDTO homeProductDTO = new HomeProductDTO();
            homeProductDTO.setCategory(category);
            List<Product> productList = productByCategory(category.getCategoryId());
            List<Product> resultProductList=new ArrayList<>();
            List<Product> zeroProductList = new ArrayList<>();
            for(Product product : productList){
                product.setPrice(getMinPrice(product.getProductId()));
                product.setStock(getTotalStock(product.getProductId()));
                if(product.getStock()==0){
                    zeroProductList.add(product);
                }else{
                    resultProductList.add(product);
                }
            }
            resultProductList.addAll(zeroProductList);
            homeProductDTO.setProductList(resultProductList);
            homeProductDTOList.add(homeProductDTO);
        }

        return homeProductDTOList;

    }

    @Override
    public void updateProduct(String productId, Double rating) {
        Product product = productRepository.findOne(productId);
        product.setRating(rating);
        productRepository.save(product);
    }
}
