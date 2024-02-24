package com.ra.service.impl;

import com.ra.entity.Product;
import com.ra.repository.impl.Repository;
import com.ra.service.ProductService;

import java.util.List;

public class ProductServiceImpl implements ProductService {
    private Repository<Product, String> productRepository;

    public ProductServiceImpl(){
        this.productRepository = new Repository<>();
    }
    @Override
    public List<Product> findAll() {
        return this.productRepository.findAll(Product.class);
    }
}
