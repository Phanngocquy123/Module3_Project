package com.ra.service;

import com.ra.entity.Account;

public interface AccountService {
    Account login(String user, String pass);
    void showAll();
    Account findId(int id);
    void add();
    void updateStatus();
    void findByNameOrUserName();
    Account findByEmpId(String id);
}
