package com.ra.service;

import com.ra.entity.Bill;

public interface GoodsSlipService {
    void findAll();
    void findAllDetail();

    void add();
    void update();
    void approve();
    void findByIdOrCode();
}
