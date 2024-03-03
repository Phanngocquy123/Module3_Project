package com.ra.entity;

import com.ra.exception.InputException;
import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.Table;

import java.text.SimpleDateFormat;

@Table(name = "Account")
public class Account {
    @Id(autoIncrement = true)
    @Column(name = "Acc_Id")
    private int accountId;
    @Column(name = "User_name")
    private String userName;
    @Column(name = "Password")
    private String password;
    @Column(name = "Permission")
    private boolean permission;
    @Column(name = "Emp_id")
    private String employeeId;
    @Column(name = "Acc_status")
    private boolean accountStatus;

    public Account() {
    }

    public Account(String userName, String password, boolean permission, String employeeId, boolean accountStatus) {
        this.userName = userName;
        this.password = password;
        this.permission = permission;
        this.employeeId = employeeId;
        this.accountStatus = accountStatus;
    }

    public int getAccountId() {
        return accountId;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public boolean isPermission() {
        return permission;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public boolean isAccountStatus() {
        return accountStatus;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public void setUserName(String userName) throws Exception{
        if (userName.isBlank())
            throw new InputException("-> Tên tài khoản không được bỏ trống");
        this.userName = userName;
    }

    public void setPassword(String password) throws Exception{
        if (password.isBlank())
            throw new InputException("-> Mật khẩu không được bỏ trống");
        this.password = password;
    }

    public void setPermission(boolean permission) {
        this.permission = permission;
    }

    public void setEmployeeId(String employeeId) throws Exception{
        if (employeeId.length() > 5)
            throw new InputException("-> Mã nhân viên <= 5 ký tự");
        this.employeeId = employeeId;
    }

    public void setAccountStatus(boolean accountStatus) {
        this.accountStatus = accountStatus;
    }

    public static void showHeader() {
        System.out.println("===========================DANH SÁCH TÀI KHOẢN===============================");
        System.out.println("| Acc_Id| User_Name        | Password     | Permission| Emp_id| Acc_Satus");
    }

    public void show() {
        System.out.printf("| %-6d| %-17s| %-13s| %-10s| %-6s| %s\n",
                this.accountId, this.userName, this.password, this.permission?"Admin":"User", this.employeeId, this.accountStatus?"Active":"Block");
    }
}
