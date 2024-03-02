package com.ra.manager.impl;

import com.ra.manager.Manager;
import com.ra.service.GoodsSlipService;
import com.ra.service.impl.BillServiceImpl;
import com.ra.service.impl.EmployeeServiceImpl;
import com.ra.service.impl.ProductServiceImpl;
import com.ra.util.Console;

public class BillManager implements Manager {
    private GoodsSlipService billService;
    public BillManager(){
        this.billService = new BillServiceImpl(employeeService.getEmployeeRepository(), productService.getProductRepository());
    }

    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    ProductServiceImpl productService = new ProductServiceImpl();

    @Override
    public void run() {
        do {
            System.out.println("***************RECEIPT MANAGEMENT***************");
            System.out.println("1. Danh sách phiếu xuất");
            System.out.println("2. Tạo phiếu xuất");
            System.out.println("3. Cập nhật thông tin phiếu xuất");
            System.out.println("4. Chi tiết phiếu xuất");
            System.out.println("5. Duyệt phiếu xuất");
            System.out.println("6. Tìm kiếm phiếu xuất");
            System.out.println("7. Thoát");
            System.out.print("Nhập lựa chọn: ");
            int choose = Integer.parseInt(Console.scanner.nextLine());
            switch (choose) {
                case 1:
                    billService.findAll();
                    break;
                case 2:
                    billService.add();
                    break;
                case 3:
                    billService.update();
                    break;
                case 4:
                    billService.findAllDetail();
                    break;
                case 5:
                    billService.approve();
                    break;
                case 6:
                    billService.findByIdOrCode();
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-7");
            }
        } while (true);
    }
}
