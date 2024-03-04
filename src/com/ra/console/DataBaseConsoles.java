package com.ra.console;

public class DataBaseConsoles {

//    DELIMITER //
//    CREATE PROCEDURE find_by_name_or_username()
//    BEGIN
//    SELECT ac.Acc_Id, ac.User_name, ac.Password, ac.Permission, ac.Emp_id, Employee.Emp_Name, ac.Acc_status
//    FROM Account ac
//    JOIN Employee ON ac.Emp_id = Employee.Emp_Id;
//    end //
//            DELIMITER ;
//    drop procedure find_by_name_or_username;
//
//
//    DELIMITER //
//    CREATE PROCEDURE find_detail_receipt()
//    BEGIN
//    SELECT bd.Bill_Detail_Id, bd.Bill_Id, bd.Product_Id, bd.Quantity, bd.Price, bill.Bill_Code, bill.Bill_Type, bill.Created
//    FROM bill_detail bd
//    JOIN bill ON bd.Bill_Detail_Id = bill.Bill_id
//    WHERE bill.Bill_Type = 1;
//    end //
//            DELIMITER ;
//    drop procedure find_detail_receipt;
//
//
//    DELIMITER //
//    CREATE PROCEDURE find_detail_bill()
//    BEGIN
//    SELECT bd.Bill_Detail_Id, bd.Bill_Id, bd.Product_Id, bd.Quantity, bd.Price, bill.Bill_Code, bill.Bill_Type, bill.Created
//    FROM bill_detail bd
//    JOIN bill ON bd.Bill_Detail_Id = bill.Bill_id
//    WHERE bill.Bill_Type = 0;
//    end //
//            DELIMITER ;
//    drop procedure find_detail_bill;



    // Các thống kê
//    -- 1. Thống kê chi phí theo ngày, tháng, năm
//    DELIMITER //
//    CREATE PROCEDURE st_receipt_by_date(IN day INT, IN month INT, IN year INT)
//    BEGIN
//    SELECT SUM(bd.Quantity * bd.Price) AS TotalCost
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//            WHERE
//    b.Bill_Type = 1 AND
//    DAY(b.Created) = day AND
//    MONTH(b.Created) = month AND
//    YEAR(b.Created) = year;
//    END //
//            DELIMITER ;
//
//-- 2. Thống kê chi phí theo khoảng thời gian
//    DELIMITER //
//    CREATE PROCEDURE st_receipt_by_date_range(IN start_day INT, IN start_month INT, IN start_year INT,
//                                              IN end_day INT, IN end_month INT, IN end_year INT)
//    BEGIN
//    SELECT SUM(bd.Quantity * bd.Price) AS TotalCost
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//    WHERE b.Bill_Type = 1
//    AND b.Created >=  STR_TO_DATE(CONCAT(start_year,'-',start_month,'-',start_day), '%Y-%m-%d')
//    AND b.Created <= STR_TO_DATE(CONCAT(end_year,'-',end_month,'-',end_day), '%Y-%m-%d') + INTERVAL 1 DAY ;
//    END //
//            DELIMITER ;
//    drop procedure st_receipt_by_date_range;
//
//-- 3. Thống kê doanh thu theo ngày, tháng, năm
//    DELIMITER //
//    CREATE PROCEDURE st_bill_by_date(IN day INT, IN month INT, IN year INT)
//    BEGIN
//    SELECT SUM(bd.Quantity * bd.Price) AS TotalCost
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//            WHERE
//    b.Bill_Type = 0 AND
//    DAY(b.Created) = day AND
//    MONTH(b.Created) = month AND
//    YEAR(b.Created) = year;
//    END //
//            DELIMITER ;
//
//-- 4. Thống kê doanh thu theo khoảng thời gian
//    DELIMITER //
//    CREATE PROCEDURE st_bill_by_date_range(IN start_day INT, IN start_month INT, IN start_year INT,
//                                           IN end_day INT, IN end_month INT, IN end_year INT)
//    BEGIN
//    SELECT SUM(bd.Quantity * bd.Price) AS TotalCost
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//    WHERE b.Bill_Type = 0
//    AND b.Created >=  STR_TO_DATE(CONCAT(start_year,'-',start_month,'-',start_day), '%Y-%m-%d')
//    AND b.Created <= STR_TO_DATE(CONCAT(end_year,'-',end_month,'-',end_day), '%Y-%m-%d') + INTERVAL 1 DAY ;
//    END //
//            DELIMITER ;
//
//-- 5. Thống kê số nhân viên theo từng trạng thái
//            DELIMITER //
//    CREATE PROCEDURE st_employee_count_by_status()
//    BEGIN
//            SELECT
//    SUM(CASE WHEN Emp_Status = 0 THEN 1 ELSE 0 END) AS Active_Count,
//    SUM(CASE WHEN Emp_Status = 1 THEN 1 ELSE 0 END) AS On_Leave_Count,
//    SUM(CASE WHEN Emp_Status = 2 THEN 1 ELSE 0 END) AS Resigned_Count
//    FROM Employee;
//    END //
//            DELIMITER ;
//
//-- 6. Thống kê sản phẩm nhập nhiều nhất trong khoảng thời gian
//            DELIMITER //
//    CREATE PROCEDURE st_max_receipt_product(IN start_day INT, IN start_month INT, IN start_year INT,
//                                            IN end_day INT, IN end_month INT, IN end_year INT)
//    BEGIN
//    SELECT bd.Product_Id, SUM(bd.Quantity) AS Total_Quantity
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//    WHERE b.Bill_Type = 1
//    AND b.Created >= STR_TO_DATE(CONCAT(start_year,'-',start_month,'-',start_day), '%Y-%m-%d')
//    AND b.Created <= STR_TO_DATE(CONCAT(end_year,'-',end_month,'-',end_day), '%Y-%m-%d') + INTERVAL 1 DAY
//    GROUP BY bd.Product_Id
//    ORDER BY SUM(bd.Quantity) DESC
//    LIMIT 1;
//    END //
//            DELIMITER ;
//    drop procedure st_max_receipt_product;
//
//-- 7. Thống kê sản phẩm nhập ít nhất trong khoảng thời gian
//            DELIMITER //
//    CREATE PROCEDURE st_min_receipt_product(IN start_day INT, IN start_month INT, IN start_year INT,
//                                            IN end_day INT, IN end_month INT, IN end_year INT)
//    BEGIN
//    SELECT bd.Product_Id, SUM(bd.Quantity) AS Total_Quantity
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//    WHERE b.Bill_Type = 1
//    AND b.Created >= STR_TO_DATE(CONCAT(start_year,'-',start_month,'-',start_day), '%Y-%m-%d')
//    AND b.Created <= STR_TO_DATE(CONCAT(end_year,'-',end_month,'-',end_day), '%Y-%m-%d') + INTERVAL 1 DAY
//    GROUP BY bd.Product_Id
//    ORDER BY SUM(bd.Quantity) ASC
//    LIMIT 1;
//    END //
//            DELIMITER ;
//
//-- 8. Thống kê sản phẩm xuất nhiều nhất trong khoảng thời gian
//            DELIMITER //
//    CREATE PROCEDURE st_max_bill_product(IN start_day INT, IN start_month INT, IN start_year INT,
//                                         IN end_day INT, IN end_month INT, IN end_year INT)
//    BEGIN
//    SELECT bd.Product_Id, SUM(bd.Quantity) AS Total_Quantity
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//    WHERE b.Bill_Type = 0
//    AND b.Created >= STR_TO_DATE(CONCAT(start_year,'-',start_month,'-',start_day), '%Y-%m-%d')
//    AND b.Created <= STR_TO_DATE(CONCAT(end_year,'-',end_month,'-',end_day), '%Y-%m-%d') + INTERVAL 1 DAY
//    GROUP BY bd.Product_Id
//    ORDER BY SUM(bd.Quantity) DESC
//    LIMIT 1;
//    END //
//            DELIMITER ;
//
//-- 9. Thống kê sản phẩm xuất ít nhất trong khoảng thời gian
//            DELIMITER //
//    CREATE PROCEDURE st_min_bill_product(IN start_day INT, IN start_month INT, IN start_year INT,
//                                         IN end_day INT, IN end_month INT, IN end_year INT)
//    BEGIN
//    SELECT bd.Product_Id, SUM(bd.Quantity) AS Total_Quantity
//    FROM Bill b
//    JOIN Bill_detail bd ON b.Bill_id = bd.Bill_Id
//    WHERE b.Bill_Type = 0
//    AND b.Created >= STR_TO_DATE(CONCAT(start_year,'-',start_month,'-',start_day), '%Y-%m-%d')
//    AND b.Created <= STR_TO_DATE(CONCAT(end_year,'-',end_month,'-',end_day), '%Y-%m-%d') + INTERVAL 1 DAY
//    GROUP BY bd.Product_Id
//    ORDER BY SUM(bd.Quantity) ASC
//    LIMIT 1;
//    END //
//            DELIMITER ;

}
