package com.ra.service;

import com.ra.entity.Employee;

public interface EmployeeService {
    void showAll();
    Employee findId(String id);
    void add();
    void update();
    void updateByStatus();
    void findByIdOrName();
}
