package com.ra.service;

import com.ra.entity.Product;

import java.util.List;

public interface ProductService {

    void showAll();
    Product findId(String id);
    void add();
    void update();
    void findByName();
    void updateStatus();
}
