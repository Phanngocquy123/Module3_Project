package com.ra.service;

public interface StatisticsService {
    void receiptCostByDate();
    void receiptCostByDateRange();

    void billCostByDate();
    void billCostByDateRange();

    void employeeCountByStatus();

    void maxReceiptProductByDateRange();
    void minReceiptProductByDateRange();

    void maxBillProductByDateRange();
    void minBillProductByDateRange();

}
