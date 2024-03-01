package com.ra.service.impl;

import com.ra.entity.Account;
import com.ra.entity.Employee;
import com.ra.entity.Product;
import com.ra.repository.impl.Repository;
import com.ra.service.EmployeeService;
import com.ra.util.Console;

import java.text.SimpleDateFormat;
import java.util.*;

public class EmployeeServiceImpl implements EmployeeService {
    private Repository<Employee, String> employeeRepository;
    private Repository<Account, Integer> accountRepository;

    AccountServiceImpl accountService = new AccountServiceImpl();

    public EmployeeServiceImpl(Repository<Account, Integer> accountRepository) {
        this.employeeRepository = new Repository<>();
        this.accountRepository = accountRepository;
    }

    public EmployeeServiceImpl() {
        this.employeeRepository = new Repository<>();
    }

    public void setAccountRepository(Repository<Account, Integer> accountRepository) {
        this.accountRepository = accountRepository;
    }
    public Repository<Employee, String> getEmployeeRepository(){
        return employeeRepository;
    }

    @Override
    public void showAll() {
        int currentPage = 1;
        final int pageSize = 10;

        List<Employee> employeeList = employeeRepository.findAll(Employee.class);
        int pageTotal = (int) Math.ceil((double) employeeList.size() / pageSize);
        tenList(currentPage, pageTotal, pageSize, employeeList);
    }

    @Override
    public Employee findId(String id) {
        return employeeRepository.findId(id, Employee.class);
    }

    @Override
    public void add() {
        do {
            System.out.println("< Thêm mới nhân viên >");
            Employee employee = new Employee();
            try {
                System.out.print("Nhập mã nhân viên: ");
                employee.setEmployeeId(Console.scanner.nextLine());
                System.out.print("Nhập tên nhân viên: ");
                employee.setEmployeeName(Console.scanner.nextLine());
                System.out.println("Nhập ngày sinh dd-MM-yyyy: ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date birthDay = dateFormat.parse(Console.scanner.nextLine());
                employee.setBirthDate(birthDay);
                System.out.print("Nhập email: ");
                employee.setEmail(Console.scanner.nextLine());
                System.out.print("Nhập số điện thoại: ");
                employee.setPhone(Console.scanner.nextLine());
                System.out.print("Nhập địa chỉ: ");
                employee.setAddress(Console.scanner.nextLine());
                System.out.print("Nhập trạng thái 0-Hoạt động; 1-Nghỉ chế độ; 2-Nghỉ việc: ");
                employee.setEmployeeStatus(Integer.parseInt(Console.scanner.nextLine()));
                employeeRepository.add(employee);
                System.out.println("Thêm nhân viên thành công! Bạn có muốn nhập tiếp (Y:có - N:Không): ");
                String choiceNext = Console.scanner.nextLine();
                if (choiceNext.equalsIgnoreCase("n")) {
                    return;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } while (true);
    }

    @Override
    public void update() {
        showAll();
        System.out.println("< Cập nhật thông tin nhân viên >");
        System.out.print("Nhập mã nhân viên muốn cập nhật: ");
        String idUpdate = Console.scanner.nextLine();
        Employee updateEmployee = new Employee();
        if (findId(idUpdate) != null) {
            try {
                updateEmployee.setEmployeeId(idUpdate);
                System.out.print("Nhập tên nhân viên: ");
                updateEmployee.setEmployeeName(Console.scanner.nextLine());
                System.out.println("Nhập ngày sinh dd-MM-yyyy: ");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date birthDay = dateFormat.parse(Console.scanner.nextLine());
                updateEmployee.setBirthDate(birthDay);
                System.out.print("Nhập email: ");
                updateEmployee.setEmail(Console.scanner.nextLine());
                System.out.print("Nhập số điện thoại: ");
                updateEmployee.setPhone(Console.scanner.nextLine());
                System.out.print("Nhập địa chỉ: ");
                updateEmployee.setAddress(Console.scanner.nextLine());
                System.out.print("Nhập trạng thái 0-Hoạt động; 1-Nghỉ chế độ; 2-Nghỉ việc: ");
                updateEmployee.setEmployeeStatus(Integer.parseInt(Console.scanner.nextLine()));
                employeeRepository.edit(updateEmployee);
                System.out.println("Cập nhật thông tin nhân viên thành công");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else
            System.out.println("Không tìm thấy nhân viên có mã Id - " + idUpdate);
    }

    @Override
    public void updateByStatus() {
        showAll();
        System.out.println("< Cập nhật trạng thái nhân viên >");
        System.out.print("Nhập mã nhân viên muốn cập nhật trạng thái: ");
        String empIdStatus = Console.scanner.nextLine();
        Employee newEmpStatus = findId(empIdStatus);
        if (newEmpStatus != null) {
            try {
                String oldStatus = Employee.convertEmployeeStatus(newEmpStatus.getEmployeeStatus());
                System.out.printf("Trạng thái hiện tại của nhân viên (Id: %s) là: %s\n", empIdStatus, oldStatus);
                System.out.print("Nhập trạng thái mới 0-Hoạt động; 1-Nghỉ chế độ; 2-Nghỉ việc: ");
                int newStatus = Integer.parseInt(Console.scanner.nextLine());
                newEmpStatus.setEmployeeStatus(newStatus);
                employeeRepository.edit(newEmpStatus);
                System.out.println("Cập nhật thông tin nhân viên Id - " + empIdStatus + " thành công");

                if (newStatus ==1 || newStatus ==2){
                    Account account = accountService.findByEmpId(empIdStatus);
                    if (account != null){
                        account.setAccountStatus(false);
                        accountRepository.edit(account);
                        System.out.printf("Nhân viên có Id - %s đã chuyển sang trạng thái: %s \n",
                                empIdStatus, account.isAccountStatus()?"Active":"Block");
                    }
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else
            System.out.println("Không tìm thấy nhân viên có mã Id - " + empIdStatus);
    }

    @Override
    public void findByIdOrName() {
        int currentPage = 1;
        final int pageSize = 10;
        System.out.print("Nhập mã hoặc tên nhân viên tìm kiếm: ");
        String stringFind = Console.scanner.nextLine();
        List<Employee> employeeResult = new ArrayList<>();
        for (Employee e : employeeRepository.findAll(Employee.class)) {
            if (e.getEmployeeId().toLowerCase().contains(stringFind.toLowerCase()) || e.getEmployeeName().toLowerCase().contains(stringFind)) {
                employeeResult.add(e);
            }
        }
        int pageTotal = (int) Math.ceil((double) employeeResult.size() / pageSize);
        tenList(currentPage, pageTotal, pageSize, employeeResult);

    }

    public void tenList(int currentPage, int pageToal, int pageSize, List<Employee> employee) {
        do {
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = currentPage * pageSize;
            //Collections.sort(employee, Comparator.comparing(Employee::getEmployeeName));
            Collections.sort(employee, (emp1, emp2) -> {
                String name1 = emp1.getEmployeeName().toLowerCase();
                String name2 = emp2.getEmployeeName().toLowerCase();
                return name1.compareTo(name2);
            });
            Employee.showHeader();
            for (int i = startIndex; i < Math.min(endIndex, employee.size()); i++) {
                employee.get(i).show();
            }
            System.out.printf("Trang: %d/%d\n", currentPage, pageToal);
            System.out.println("<--(1) Trang trước __ Trang sau (2)-->");
            System.out.println("(3) Trở lại");
            System.out.print("Nhập lựa chọn (1) (2) (3): ");
            int choice = Integer.parseInt(Console.scanner.nextLine());

            switch (choice) {
                case 1:
                    if (currentPage > 1) {
                        currentPage--;
                    } else {
                        System.out.println("-> Đây là trang đầu tiên!");
                    }
                    break;
                case 2:
                    if (currentPage < pageToal) {
                        currentPage++;
                    } else {
                        System.out.println("-> Đây là trang cuối cùng!");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-3");
            }
        } while (true);
    }
}
