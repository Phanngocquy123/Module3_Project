package com.ra.manager.impl;

import com.ra.manager.Manager;
import com.ra.service.AccountService;
import com.ra.service.impl.AccountServiceImpl;
import com.ra.util.Console;

import java.security.PublicKey;

public class AccountManager implements Manager {
    private AccountService accountService;
    public AccountManager(){
        this.accountService = new AccountServiceImpl();
    }
    @Override
    public void run() {
        do {
            System.out.println("***************ACCOUNT MANAGEMENT***************");
            System.out.println("1. Danh sách tài khoản");
            System.out.println("2. Tạo tài khoản mới");
            System.out.println("3. Cập nhật trạng thái tài khoản");
            System.out.println("4. Tìm kiếm tài khoản");
            System.out.println("5. Thoát");
            System.out.print("Nhập lựa chọn: ");
            int choose = Integer.parseInt(Console.scanner.nextLine());
            switch (choose) {
                case 1:
                    accountService.showAll();
                    break;
                case 2:
                    accountService.add();
                    break;
                case 3:
                    accountService.updateStatus();
                    break;
                case 4:
                    accountService.findByNameOrUserName();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-5");
            }
        } while (true);
    }
}
