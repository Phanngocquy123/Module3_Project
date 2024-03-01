package com.ra.service;

import com.ra.entity.Bill;

public interface ReceiptService {
    void findAll();
    void findAllDetail();

    void add();
    void update();
    void approve();
}
