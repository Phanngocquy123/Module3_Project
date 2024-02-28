package com.ra.service.impl;

import com.ra.entity.Bill;
import com.ra.entity.BillDetail;
import com.ra.entity.Employee;
import com.ra.repository.impl.Repository;
import com.ra.service.EmployeeService;
import com.ra.service.ReceiptService;
import com.ra.util.Console;
import com.ra.util.MySqlConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;


public class ReceiptServiceImpl implements ReceiptService {
    private Repository<Bill, Long> receiptRepository;
    private Repository<BillDetail, Long> receiptDetailRepository;
    private Repository<Employee, String> employeeRepository;
    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();

    public ReceiptServiceImpl(Repository<Employee, String> employeeRepository){
        this.receiptRepository = new Repository<>();
        this.receiptDetailRepository = new Repository<>();
        this.employeeRepository = employeeRepository;
    }



    @Override
    public void findAll() {
        Bill.showHeader();
        for (Bill b : receiptRepository.findAll(Bill.class)){
            if (b.isBillType()){
                b.show();
            }
        }
    }

    @Override
    public void findAllDetail() {
        Connection conn = null;
        System.out.println("===============DANH SÁCH CHI TIẾT PHIẾU NHẬP===================");
        System.out.println("| Bill_Detail_Id| Bill_Id| Bill_code| Product_Id| Quantity| Price");
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call find_detail_receipt()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()){
                Long billDetailId = rs.getLong("Bill_Detail_Id");
                Long billId = rs.getLong("Bill_Id");
                String productId = rs.getString("Product_Id");
                int quantity = rs.getInt("Quantity");
                float price = rs.getFloat("Price");
                String formattedPrice = String.format("%.2f", price);
                String billCode = rs.getString("Bill_Code");
                System.out.printf("| %-14d| %-7d| %-9s| %-10s| %-8d| %s\n",
                        billDetailId, billId, billCode, productId, quantity, formattedPrice);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void add() {
        do {
            System.out.println("< Tạo phiếu nhập >");
            Bill bill = new Bill();
            BillDetail billDetail = new BillDetail();
            try {
                System.out.print("Nhập mã code: ");
                bill.setBillCode(Console.scanner.nextLine());
                bill.setBillType(true);
                System.out.println("Nhập mã nhân viên nhập");
                employeeService.showAll();
              //  bill.setEmployeeIdCreated();
              //  bill.setCreated(new Date());

                return;
            } catch (Exception ex){
                ex.printStackTrace();
            }
        } while (true);
    }
}
