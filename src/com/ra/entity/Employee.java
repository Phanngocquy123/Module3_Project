package com.ra.entity;

import com.ra.exception.InputException;
import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.Table;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Table(name = "Employee")
public class Employee {
    @Id(autoIncrement = false)
    @Column(name = "Emp_Id")
    private String employeeId;
    @Column(name = "Emp_Name")
    private String employeeName;
    @Column(name = "Birth_Of_Date")
    private Date birthDate;
    @Column(name = "Email")
    private String email;
    @Column(name = "Phone")
    private String phone;
    @Column(name = "Address")
    private String address;
    @Column(name = "Emp_Status")
    private int employeeStatus;

    public Employee() {
    }

    public Employee(String employeeId, String employeeName, Date birthDate, String email, String phone, String address, int employeeStatus) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.birthDate = birthDate;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.employeeStatus = employeeStatus;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getEmployeeStatus() {
        return employeeStatus;
    }

    public void setEmployeeId(String employeeId) throws Exception {
        if (employeeId.length() != 5)
            throw new InputException("-> Mã nhân viên phải gồm 5 ký tự");
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) throws Exception {
        if (employeeName.isBlank())
            throw new InputException("-> Tên nhân viên không được bỏ trống");
        this.employeeName = employeeName;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public void setEmail(String email) throws Exception {
        if (email.isBlank())
            throw new InputException("-> Email không được bỏ trống");
        this.email = email;
    }

    public void setPhone(String phone) throws Exception {
        if (phone.isBlank())
            throw new InputException("-> Số điện thoại không được bỏ trống");
        this.phone = phone;
    }

    public void setAddress(String address) throws Exception {
        if (address.isBlank())
            throw new InputException("-> Địa chỉ không được bỏ trống");
        this.address = address;
    }

    public void setEmployeeStatus(int employeeStatus) throws Exception {
        if (employeeStatus != 0 && employeeStatus != 1 && employeeStatus != 2)
            throw new InputException("-> Trạng thái: 0-Hoạt động; 1-Nghỉ chế độ; 2-Nghỉ việc");
        this.employeeStatus = employeeStatus;
    }

    public static void showHeader() {
        System.out.println("===========================================DANH SÁCH NHÂN VIÊN===========================================");
        System.out.println("| Emp_Id| Emp_Name        | Birth_Of_Date | Email                     | Phone     | Address            | Emp_Satus");
    }

    public void show() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = (birthDate != null) ? dateFormat.format(birthDate) : "";
        String statusResult = convertEmployeeStatus(this.employeeStatus);
        System.out.printf("| %-6s| %-16s| %-14s| %-26s| %-10s| %-19s| %s\n",
                this.employeeId, this.employeeName, formattedDate, this.email, this.phone, this.address, statusResult);
    }

    public static String convertEmployeeStatus(int employeeStatus) {
        return switch (employeeStatus) {
            case 0 -> "Hoạt động";
            case 1 -> "Nghỉ chế độ";
            case 2 -> "Nghỉ việc";
            default -> "Trạng thái không xác định";
        };
    }
}
