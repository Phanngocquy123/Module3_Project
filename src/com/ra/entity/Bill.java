package com.ra.entity;

import com.ra.exception.InputException;
import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.Table;

import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "Bill")
public class Bill {
    @Id(autoIncrement = true)
    @Column(name = "Bill_id")
    private long billId;
    @Column(name = "Bill_Code")
    private String billCode;
    @Column(name = "Bill_Type")
    private boolean billType;
    @Column(name = "Emp_id_created")
    private String employeeIdCreated;
    @Column(name = "Created")
    private Date created;
    @Column(name = "Emp_id_auth")
    private String employeeIdAuth;
    @Column(name = "Auth_date")
    private Date authDate;
    @Column(name = "Bill_Status")
    private int billStatus;

    public Bill() {
    }

    public Bill(long billId, String billCode, boolean billType, String employeeIdCreated, Date created, String employeeIdAuth, Date authDate, int billStatus) {
        this.billId = billId;
        this.billCode = billCode;
        this.billType = billType;
        this.employeeIdCreated = employeeIdCreated;
        this.created = created;
        this.employeeIdAuth = employeeIdAuth;
        this.authDate = authDate;
        this.billStatus = billStatus;
    }

    public long getBillId() {
        return billId;
    }

    public String getBillCode() {
        return billCode;
    }

    public boolean isBillType() {
        return billType;
    }

    public String getEmployeeIdCreated() {
        return employeeIdCreated;
    }

    public Date getCreated() {
        return created;
    }

    public String getEmployeeIdAuth() {
        return employeeIdAuth;
    }

    public Date getAuthDate() {
        return authDate;
    }

    public int getBillStatus() {
        return billStatus;
    }

    public void setBillId(long billId) {
        this.billId = billId;
    }

    public void setBillCode(String billCode) throws Exception{
        if (billCode.length() > 10)
            throw new InputException("-> Mã code không vượt quá 10 ký tự");
        this.billCode = billCode;
    }

    public void setBillType(boolean billType) throws Exception{
      //  if (billType != 0 && billType != 1)
      //      throw new InputException("-> Loại phiếu: 0 - Xuất;  1 - Nhập");
        this.billType = billType;
    }

    public void setEmployeeIdCreated(String employeeIdCreated) throws Exception{
        this.employeeIdCreated = employeeIdCreated;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setEmployeeIdAuth(String employeeIdAuth) throws Exception{
        if (employeeIdAuth.length() != 5)
            throw new InputException("-> Mã nhân viên duyệt phải gồm 5 ký tự");
        this.employeeIdAuth = employeeIdAuth;
    }

    public void setAuthDate(Date authDate) {
        this.authDate = authDate;
    }

    public void setBillStatus(int billStatus) throws Exception{
        if (billStatus != 0 && billStatus != 1 && billStatus != 2)
            throw new InputException("-> Trạng thái: 0-Tạo; 1-Hủy;  2-Duyệt");
        this.billStatus = billStatus;
    }

    public static void showHeader(){
        System.out.println("===========================================DANH SÁCH PHIẾU NHẬP===============================================");
        System.out.println("| Bill_Id| Bill_Code | Bill_Type| Emp_id_created| Created            | Emp_id_auth| Auth_date           | Bill_status");
    }
    public void show(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
        String formattedDate = dateFormat.format(this.created);
        String statusResult = convertBillStatus(this.billStatus);
        System.out.printf("| %-7d| %-10s| %-9s| %-14s| %-19s| %-11s| %-19s |%s\n",
                this.billId, this.billCode, this.billType?"Nhập":"Xuất", this.employeeIdCreated, formattedDate, this.employeeIdAuth, formattedDate, statusResult);
    }

    public static String convertBillStatus(int billStatus) {
        return switch (billStatus) {
            case 0 -> "Tạo";
            case 1 -> "Hủy";
            case 2 -> "Duyệt";
            default -> "Trạng thái không xác định";
        };
    }
}
