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
    public void receiptCostByDateRange() {                     // 2. Thống kê chi phí theo khoảng thời gian
        int[] dateInfo = dateInput();
        int startDay = dateInfo[0];
        int startMonth = dateInfo[1];
        int startYear = dateInfo[2];
        int endDay = dateInfo[3];
        int endMonth = dateInfo[4];
        int endYear = dateInfo[5];
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_receipt_by_date_range(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, startDay);
            cs.setInt(2, startMonth);
            cs.setInt(3, startYear);
            cs.setInt(4, endDay);
            cs.setInt(5, endMonth);
            cs.setInt(6, endYear);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                float totalCost = rs.getFloat("TotalCost");
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.printf("Thống kê chi phí từ ngày %d/%d/%d đến %d/%d/%d là: %s\n",
                        startDay, startMonth, startYear, endDay, endMonth, endYear, df.format(totalCost));
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

    @Override
    public void billCostByDateRange() {                  // 4. Thống kê doanh thu theo khoảng thời gian
        int[] dateInfo = dateInput();
        int startDay = dateInfo[0];
        int startMonth = dateInfo[1];
        int startYear = dateInfo[2];
        int endDay = dateInfo[3];
        int endMonth = dateInfo[4];
        int endYear = dateInfo[5];
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_bill_by_date_range(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, startDay);
            cs.setInt(2, startMonth);
            cs.setInt(3, startYear);
            cs.setInt(4, endDay);
            cs.setInt(5, endMonth);
            cs.setInt(6, endYear);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                float totalCost = rs.getFloat("TotalCost");
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.printf("Thống kê doanh thu từ ngày %d/%d/%d đến %d/%d/%d là: %s\n",
                        startDay, startMonth, startYear, endDay, endMonth, endYear, df.format(totalCost));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void employeeCountByStatus() {                      //  5. Thống kê số nhân viên theo từng trạng thái
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_employee_count_by_status()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                int active = rs.getInt("Active_Count");
                int onLeave = rs.getInt("On_Leave_Count");
                int resigned = rs.getInt("Resigned_Count");
                System.out.printf("Thống kê số nhân viên theo trạng thái: Hoạt động: %d NV _ Nghỉ chế độ: %d NV _ Nghỉ việc: %d NV\n",
                        active, onLeave, resigned);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void maxReceiptProductByDateRange() {
        int[] dateInfo = dateInput();
        int startDay = dateInfo[0];
        int startMonth = dateInfo[1];
        int startYear = dateInfo[2];
        int endDay = dateInfo[3];
        int endMonth = dateInfo[4];
        int endYear = dateInfo[5];
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_max_receipt_product(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, startDay);
            cs.setInt(2, startMonth);
            cs.setInt(3, startYear);
            cs.setInt(4, endDay);
            cs.setInt(5, endMonth);
            cs.setInt(6, endYear);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("Product_Id");
                int maxQuantity = rs.getInt("Total_Quantity");
                System.out.printf("Thống kê SP có số lượng nhập lớn nhất từ ngày %d/%d/%d đến %d/%d/%d là: " +
                                "ProductId - %s, %d (sản phẩm)\n",
                        startDay, startMonth, startYear, endDay, endMonth, endYear, productId, maxQuantity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void minReceiptProductByDateRange() {                       // 7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian
        System.out.println("Từ: ");
        System.out.print("Nhập ngày: ");
        int startDay = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập tháng: ");
        int startMonth = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập năm: ");
        int startYear = Integer.parseInt(Console.scanner.nextLine());
        System.out.println("Đến: ");
        System.out.print("Nhập ngày: ");
        int endDay = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập tháng: ");
        int endMonth = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập năm: ");
        int endYear = Integer.parseInt(Console.scanner.nextLine());
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_min_receipt_product(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, startDay);
            cs.setInt(2, startMonth);
            cs.setInt(3, startYear);
            cs.setInt(4, endDay);
            cs.setInt(5, endMonth);
            cs.setInt(6, endYear);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("Product_Id");
                int maxQuantity = rs.getInt("Total_Quantity");
                System.out.printf("Thống kê SP có số lượng nhập ít nhất từ ngày %d/%d/%d đến %d/%d/%d là: " +
                                "ProductId - %s, %d (sản phẩm)\n",
                        startDay, startMonth, startYear, endDay, endMonth, endYear, productId, maxQuantity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void maxBillProductByDateRange() {                     //  8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian
        int[] dateInfo = dateInput();
        int startDay = dateInfo[0];
        int startMonth = dateInfo[1];
        int startYear = dateInfo[2];
        int endDay = dateInfo[3];
        int endMonth = dateInfo[4];
        int endYear = dateInfo[5];
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_max_bill_product(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, startDay);
            cs.setInt(2, startMonth);
            cs.setInt(3, startYear);
            cs.setInt(4, endDay);
            cs.setInt(5, endMonth);
            cs.setInt(6, endYear);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("Product_Id");
                int maxQuantity = rs.getInt("Total_Quantity");
                System.out.printf("Thống kê SP có số lượng xuất lớn nhất từ ngày %d/%d/%d đến %d/%d/%d là: " +
                                "ProductId - %s, %d (sản phẩm)\n",
                        startDay, startMonth, startYear, endDay, endMonth, endYear, productId, maxQuantity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void minBillProductByDateRange() {                     // 9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian
        int[] dateInfo = dateInput();
        int startDay = dateInfo[0];
        int startMonth = dateInfo[1];
        int startYear = dateInfo[2];
        int endDay = dateInfo[3];
        int endMonth = dateInfo[4];
        int endYear = dateInfo[5];
        Connection conn = null;
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call st_min_bill_product(?, ?, ?, ?, ?, ?)}");
            cs.setInt(1, startDay);
            cs.setInt(2, startMonth);
            cs.setInt(3, startYear);
            cs.setInt(4, endDay);
            cs.setInt(5, endMonth);
            cs.setInt(6, endYear);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                String productId = rs.getString("Product_Id");
                int maxQuantity = rs.getInt("Total_Quantity");
                System.out.printf("Thống kê SP có số lượng xuất ít nhất từ ngày %d/%d/%d đến %d/%d/%d là: " +
                                "ProductId - %s, %d (sản phẩm)\n",
                        startDay, startMonth, startYear, endDay, endMonth, endYear, productId, maxQuantity);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    public int[] dateInput(){
        int[] dateInfo = new int[6];
        System.out.println("Từ: ");
        System.out.print("Nhập ngày: ");
        dateInfo[0] = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập tháng: ");
        dateInfo[1] = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập năm: ");
        dateInfo[2] = Integer.parseInt(Console.scanner.nextLine());
        System.out.println("Đến: ");
        System.out.print("Nhập ngày: ");
        dateInfo[3] = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập tháng: ");
        dateInfo[4] = Integer.parseInt(Console.scanner.nextLine());
        System.out.print("Nhập năm: ");
        dateInfo[5] = Integer.parseInt(Console.scanner.nextLine());
        return dateInfo;
    }
}
