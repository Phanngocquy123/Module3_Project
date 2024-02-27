package com.ra.service.impl;

import com.ra.entity.Account;
import com.ra.repository.impl.Repository;
import com.ra.service.AccountService;
import com.ra.util.Console;
import com.ra.util.MySqlConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

public class AccountServiceImpl implements AccountService {
    private Repository<Account, Integer> accountRepository;
    public AccountServiceImpl(){
        this.accountRepository = new Repository<>();
    }
    // Phương thức getter để lấy accountRepository
    public Repository<Account, Integer> getAccountRepository() {
        return accountRepository;
    }



    @Override
    public void showAll() {
        Account.showHeader();
        for (Account a : accountRepository.findAll(Account.class)){
            a.show();
        }
    }

    @Override
    public Account findId(int id) {
        return accountRepository.findId(id, Account.class);
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

    @Override
    public void updateStatus() {
        System.out.print("Nhập mã tài khoản muốn cập nhật: ");
        int id = Integer.parseInt(Console.scanner.nextLine());
        Account newAccStatus = findId(id);
        if (newAccStatus != null){
            System.out.printf("Trạng thái hiện tại của tài khoản có Id - %d là: %s\n",
                    id, newAccStatus.isAccountStatus()?"Active":"Block");
            System.out.print("Nhập trạng thái mới true-Active   false-Block: ");
            boolean newStatus = Boolean.parseBoolean(Console.scanner.nextLine());
            newAccStatus.setAccountStatus(newStatus);
            accountRepository.edit(newAccStatus);
            System.out.println("Cập nhật trạng thái tài khoảng Id - "+id+" thành công!");
        } else
            System.out.println("Tài khoản có Id - "+id+" không tồn tại!");
    }

    @Override
    public void findByNameOrUserName() {
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call find_by_name_or_username()}");
            ResultSet rs = cs.executeQuery(); // Thực thi thủ tục
            System.out.print("Nhập tên nhân viên hoặc username tìm kiếm: ");
            String search = Console.scanner.nextLine().toLowerCase();
            int count = 0;
            System.out.println("| Acc_Id| User_Name        | Password     | Permission| Emp_id| Emp_Name        | Acc_Satus");
            while (rs.next()) {
                int accId = rs.getInt("Acc_Id");
                String userName = rs.getString("User_name");
                String password = rs.getString("Password");
                boolean permission = rs.getBoolean("Permission");
                String empId = rs.getString("Emp_id");
                String empName = rs.getString("Emp_Name");
                boolean accStatus = rs.getBoolean("Acc_status");

                if (userName.toLowerCase().contains(search) || empName.toLowerCase().contains(search)) {
                    count++;
                    System.out.printf("| %-6d| %-17s| %-13s| %-10s| %-6s| %-16s| %s\n",
                            accId, userName, password, permission?"Admin":"User", empId, empName, accStatus?"Active":"Block");
                }
            }
            if (count ==0){
                System.out.println("Không tìm thấy!");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public  Account findByEmpId(String id) {
        for (Account a : accountRepository.findAll(Account.class)){
            if (a.getEmployeeId().equals(id)){
                return a;
            }
        }
        return null;
    }
}
