package com.ra;

import com.ra.manager.Manager;
import com.ra.manager.impl.*;
import com.ra.util.Console;

public class Application {
    public static void main(String[] args) {
        Manager productManager = new ProductManager();
        Manager employeeManager = new EmployeeManager();
        Manager accountManager = new AccountManager();
        Manager receiptManager = new ReceiptManager();
        Manager billManager = new BillManager();
        do {
            System.out.println("ADMIN***************WAREHOUSE MANAGEMENT***************");
            System.out.println("1. Quản lý sản phẩm");
            System.out.println("2. Quản lý nhân viên");
            System.out.println("3. Quản lý tài khoản");
            System.out.println("4. Quản lý phiếu nhập");
            System.out.println("5. Quản lý phiếu xuất");
            System.out.println("6. Quản lý báo cáo");
            System.out.println("7. Thoát");
            System.out.print("Chọn chức năng: ");
            int choose = Integer.parseInt(Console.scanner.nextLine());
            switch (choose){
                case 1:
                    productManager.run();
                    break;
                case 2:
                    employeeManager.run();
                    break;
                case 3:
                    accountManager.run();
                    break;
                case 4:
                    receiptManager.run();
                    break;
                case 5:
                    billManager.run();
                    break;
                case 6:
                    break;
                case 7:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-7");
            }
        } while (true);











//
//        System.out.println("***************REPORT MANAGEMENT***************");
//        System.out.println("1. Thống kê chi phí theo ngày, tháng, năm");
//        System.out.println("2. Thống kê chi phí theo khoảng thời gian");
//        System.out.println("3. Thống kê doanh thu theo ngày, tháng, năm");
//        System.out.println("4. Thống kê doanh thu theo khoảng thời gian");
//        System.out.println("5. Thống kê số nhân viên theo từng trạng thái");
//        System.out.println("6. Thống kê sản phẩm nhập nhiều nhất trong khoảng thời gian");
//        System.out.println("7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian");
//        System.out.println("8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian");
//        System.out.println("9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian");
//        System.out.println("10. Thoát");
//
//        System.out.println("USER***************WAREHOUSE MANAGEMENT***************");
//        System.out.println("1. Danh sách phiếu nhập theo trạng thái");
//        System.out.println("2. Tạo phiếu nhập");
//        System.out.println("3. Cập nhật phiếu nhập");
//        System.out.println("4. Tìm kiếm phiếu nhập");
//        System.out.println("5. Danh sách phiếu xuất theo trạng thái");
//        System.out.println("6. Tạo phiếu xuất");
//        System.out.println("7. Cập nhật phiếu xuất");
//        System.out.println("8. Tìm kiếm phiếu xuất");
//        System.out.println("9. Thoát");



    }
}
