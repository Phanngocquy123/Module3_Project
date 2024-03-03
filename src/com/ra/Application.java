package com.ra;

import com.ra.entity.Account;
import com.ra.entity.Bill;
import com.ra.manager.Manager;
import com.ra.manager.impl.*;
import com.ra.model.PermissionType;
import com.ra.repository.impl.Repository;
import com.ra.service.AccountService;
import com.ra.service.GoodsSlipService;
import com.ra.service.impl.AccountServiceImpl;
import com.ra.service.impl.BillServiceImpl;
import com.ra.service.impl.ReceiptServiceImpl;
import com.ra.util.Console;

public class Application {
    public static void main(String[] args) {
        AccountService accountService = new AccountServiceImpl();
        System.out.print("Nhập tài khoản: ");
        String user = Console.scanner.nextLine();
        System.out.print("Nhập mật khẩu: ");
        String pass = Console.scanner.nextLine();
        Account acc = accountService.login(user, pass);


        Manager productManager = new ProductManager();
        Manager employeeManager = new EmployeeManager();
        Manager accountManager = new AccountManager();
        Manager receiptManager = new ReceiptManager();
        Manager billManager = new BillManager();
        Manager statisticsManager = new StatisticsManager();

        GoodsSlipService receiptService = new ReceiptServiceImpl();
        GoodsSlipService billService = new BillServiceImpl();

        if (acc == null) {
            System.out.println("Tài khoản không tồn tại hoặc đã bị khóa");
        } else {
            if (PermissionType.ADMIN == acc.isPermission()) {
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
                    switch (choose) {
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
                            statisticsManager.run();
                            break;
                        case 7:
                            return;
                        default:
                            System.out.println("Hãy nhập từ 1-7");
                    }
                } while (true);
            } else {
                do {
                    String empIdAcc = acc.getEmployeeId();
                    System.out.println("USER***************WAREHOUSE MANAGEMENT***************");
                    System.out.println("1. Danh sách phiếu nhập theo trạng thái");
                    System.out.println("2. Tạo phiếu nhập");
                    System.out.println("3. Cập nhật phiếu nhập");
                    System.out.println("4. Tìm kiếm phiếu nhập");
                    System.out.println("5. Danh sách phiếu xuất theo trạng thái");
                    System.out.println("6. Tạo phiếu xuất");
                    System.out.println("7. Cập nhật phiếu xuất");
                    System.out.println("8. Tìm kiếm phiếu xuất");
                    System.out.println("9. Thoát");
                    System.out.print("Chọn chức năng: ");
                    int choose = Integer.parseInt(Console.scanner.nextLine());
                    switch (choose) {
                        case 1:
                            receiptService.listByStatus(empIdAcc);
                            break;
                        case 2:
                            receiptService.add(empIdAcc);
                            break;
                        case 3:
                            receiptService.update(empIdAcc);
                            break;
                        case 4:
                            receiptService.findByIdOrCode(empIdAcc);
                            break;
                        case 5:
                            billService.listByStatus(empIdAcc);
                            break;
                        case 6:
                            billService.add(empIdAcc);
                            break;
                        case 7:
                            billService.update(empIdAcc);
                            break;
                        case 8:
                            billService.findByIdOrCode(empIdAcc);
                            break;
                        case 9:
                            return;
                        default:
                            System.out.println("Hãy nhập từ 1-9");
                    }
                } while (true);
            }
        }


//

//


    }
}
