package com.ra.service.impl;

import com.ra.service.StatisticsService;
import com.ra.util.Console;
import com.ra.util.MySqlConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class StatisticsServiceImpl implements StatisticsService {

    @Override
    public void receiptCostByDate() {              // 1. Thống kê chi phí theo ngày, tháng, năm
        System.out.print("Nhập ngày: ");
        int day = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập tháng: ");
        int month = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập năm: ");
        int year = Integer.parseInt(Console.scanner.nextLine());
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_receipt_by_date(?, ?, ?)}");
            cs.setInt(1, day);
            cs.setInt(2, month);
            cs.setInt(3, year);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                float totalCost = rs.getFloat("TotalCost");
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.printf("Thống kê chi phí theo ngày %d/%d/%d là: %s\n",
                        day, month, year, df.format(totalCost));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }


    @Override
    public void billCostByDate() {                      // 3. Thống kê doanh thu theo ngày, tháng, năm
        System.out.print("Nhập ngày: ");
        int day = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập tháng: ");
        int month = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập năm: ");
        int year = Integer.parseInt(Console.scanner.nextLine());
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_bill_by_date(?, ?, ?)}");
            cs.setInt(1, day);
            cs.setInt(2, month);
            cs.setInt(3, year);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                float totalCost = rs.getFloat("TotalCost");
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.printf("Thống kê doanh thu theo ngày %d/%d/%d là: %s\n",
                        day, month, year, df.format(totalCost));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }
}
