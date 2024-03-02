package com.ra.manager.impl;

import com.ra.manager.Manager;
import com.ra.service.StatisticsService;
import com.ra.service.impl.StatisticsServiceImpl;
import com.ra.util.Console;

public class StatisticsManager implements Manager {
    private StatisticsService statisticsService;
    public StatisticsManager(){
        this.statisticsService = new StatisticsServiceImpl();
    }
    @Override
    public void run() {
        do {
            System.out.println("***************REPORT MANAGEMENT***************");
            System.out.println("1. Thống kê chi phí theo ngày, tháng, năm");
            System.out.println("2. Thống kê chi phí theo khoảng thời gian");
            System.out.println("3. Thống kê doanh thu theo ngày, tháng, năm");
            System.out.println("4. Thống kê doanh thu theo khoảng thời gian");
            System.out.println("5. Thống kê số nhân viên theo từng trạng thái");
            System.out.println("6. Thống kê sản phẩm nhập nhiều nhất trong khoảng thời gian");
            System.out.println("7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian");
            System.out.println("8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian");
            System.out.println("9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian");
            System.out.println("10. Thoát");
            System.out.print("Nhập lựa chọn: ");
            int choose = Integer.parseInt(Console.scanner.nextLine());
            switch (choose) {
                case 1:
                    statisticsService.receiptCostByDate();
                    break;
                case 2:

                    break;
                case 3:
                    statisticsService.billCostByDate();
                    break;
                case 4:

                    break;
                case 5:

                    break;
                case 6:
                    break;
                case 7:
                    break;
                case 8:
                    break;
                case 9:
                    break;
                case 10:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-10");
            }
        }while (true);
    }
}
