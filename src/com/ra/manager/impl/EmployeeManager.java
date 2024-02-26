package com.ra.manager.impl;

import com.ra.manager.Manager;
import com.ra.service.EmployeeService;
import com.ra.service.impl.EmployeeServiceImpl;
import com.ra.util.Console;

public class EmployeeManager implements Manager {
    private EmployeeService employeeService;
    public EmployeeManager(){
        this.employeeService = new EmployeeServiceImpl();
    }
    @Override
    public void run() {
        do {
            System.out.println("***************EMPLOYEE MANAGEMENT***************");
            System.out.println("1. Danh sách nhân viên");
            System.out.println("2. Thêm mới nhân viên");
            System.out.println("3. Cập nhật thông tin nhân viên");
            System.out.println("4. Cập nhật trạng thái nhân viên");
            System.out.println("5. Tìm kiếm nhân viên");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng: ");
            int choose = Integer.parseInt(Console.scanner.nextLine());
            switch (choose) {
                case 1:
                    employeeService.showAll();
                    break;
                case 2:
                    employeeService.add();
                    break;
                case 3:
                    employeeService.update();
                    break;
                case 4:
                    employeeService.updateByStatus();
                    break;
                case 5:
                    employeeService.findByIdOrName();
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-6");
            }
        }while (true);
    }
}
