package com.ra.service;

import com.ra.entity.Bill;

public interface GoodsSlipService {
    void findAll();
    void findAllDetail();

    void add(String empIdAcc);
    void update(String empIdAcc);
    void approve();
    void findByIdOrCode(String empIdAcc);

    void listByStatus(String empIdAcc);
}
