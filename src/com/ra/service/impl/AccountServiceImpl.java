package com.ra.service.impl;

import com.ra.entity.Account;
import com.ra.repository.impl.Repository;
import com.ra.service.AccountService;
import com.ra.util.Console;

import java.util.List;

public class AccountServiceImpl implements AccountService {
    private Repository<Account, Integer> accountRepository;
    public AccountServiceImpl(){
        this.accountRepository = new Repository<>();
    }

    @Override
    public void showAll() {
        Account.showHeader();
        for (Account a : accountRepository.findAll(Account.class)){
            a.show();
        }
    }

    @Override
    public void add() {
        do {
            System.out.println("< Thêm tài khoản >");
            Account account = new Account();
            try {
                System.out.print("Nhập tên tài khoản: ");
                account.setUserName(Console.scanner.nextLine());
                System.out.print("Nhập mật khẩu: ");
                account.setPassword(Console.scanner.nextLine());
                System.out.print("Nhập quyền tài khoản false-admin  true-user: ");
                account.setPermission(Boolean.parseBoolean(Console.scanner.nextLine()));
                System.out.print("Nhập mã nhân viên: ");
                account.setEmployeeId(Console.scanner.nextLine());
                System.out.print("Nhập trạng thái: ");
                account.setAccountStatus(Boolean.parseBoolean(Console.scanner.nextLine()));
                accountRepository.add(account);
                System.out.println("Thêm tài khoản thành công! Bạn có muốn nhập tiếp (Y:có - N:Không): ");
                String choiceNext = Console.scanner.nextLine();
                if (choiceNext.equalsIgnoreCase("n")) {
                    return;
                }
            } catch (Exception ex){
                ex.printStackTrace();
            }
        }while (true);
    }
}
