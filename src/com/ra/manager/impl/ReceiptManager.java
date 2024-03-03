package com.ra.manager.impl;

import com.ra.manager.Manager;
import com.ra.service.GoodsSlipService;
import com.ra.service.impl.EmployeeServiceImpl;
import com.ra.service.impl.ProductServiceImpl;
import com.ra.service.impl.ReceiptServiceImpl;
import com.ra.util.Console;

public class ReceiptManager implements Manager {
    private GoodsSlipService receiptService;
    public ReceiptManager(){
        this.receiptService = new ReceiptServiceImpl(employeeService.getEmployeeRepository(), productService.getProductRepository());
    }

    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    ProductServiceImpl productService = new ProductServiceImpl();
    String empIdAcc = null;

    @Override
    public void run() {
        do {
            System.out.println("***************RECEIPT MANAGEMENT***************");
            System.out.println("1. Danh sách phiếu nhập");
            System.out.println("2. Tạo phiếu nhập");
            System.out.println("3. Cập nhật thông tin phiếu nhập");
            System.out.println("4. Chi tiết phiếu nhập");
            System.out.println("5. Duyệt phiếu nhập");
            System.out.println("6. Tìm kiếm phiếu nhập");
            System.out.println("7. Thoát");
            System.out.print("Nhập lựa chọn: ");
            int choose = Integer.parseInt(Console.scanner.nextLine());
            switch (choose) {
                case 1:
                    receiptService.findAll();
                    break;
                case 2:
                    receiptService.add(empIdAcc);
                    break;
                case 3:
                    receiptService.update(empIdAcc);
                    break;
                case 4:
                    receiptService.findAllDetail();
                    break;
                case 5:
                    receiptService.approve();
                    break;
                case 6:
                    receiptService.findByIdOrCode(empIdAcc);
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-7");
            }
        } while (true);
    }
}
