package com.ra.service.impl;

import com.ra.entity.Bill;
import com.ra.entity.BillDetail;
import com.ra.entity.Employee;
import com.ra.entity.Product;
import com.ra.repository.impl.Repository;
import com.ra.service.GoodsSlipService;
import com.ra.util.Console;
import com.ra.util.MySqlConnect;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

public class BillServiceImpl implements GoodsSlipService{
    private Repository<Bill, Long> billRepository;
    private Repository<BillDetail, Long> billDetailRepository;
    private Repository<Employee, String> employeeRepository;
    private Repository<Product, String> productRepository;

    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    ProductServiceImpl productService = new ProductServiceImpl();

    public BillServiceImpl(Repository<Employee, String> employeeRepository, Repository<Product, String> productRepository) {
        this.billRepository = new Repository<>();
        this.billDetailRepository = new Repository<>();
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }


    @Override
    public void findAll() {
        Bill.showHeader();
        for (Bill b : billRepository.findAll(Bill.class)) {
            if (!b.isBillType())
                b.show();
        }
    }

    @Override
    public void findAllDetail() {
        Connection conn = null;
        System.out.println("===============DANH SÁCH CHI TIẾT PHIẾU XUẤT===================");
        System.out.println("| Bill_Detail_Id| Bill_Id| Bill_code| Product_Id| Quantity| Price");
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call find_detail_bill()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Long billDetailId = rs.getLong("Bill_Detail_Id");
                Long billId = rs.getLong("Bill_Id");
                String productId = rs.getString("Product_Id");
                int quantity = rs.getInt("Quantity");
                float price = rs.getFloat("Price");
                String formattedPrice = String.format("%.2f", price);
                String billCode = rs.getString("Bill_Code");
                System.out.printf("| %-14d| %-7d| %-9s| %-10s| %-8d| %s\n",
                        billDetailId, billId, billCode, productId, quantity, formattedPrice);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void add() {
        do {
            System.out.println("< Tạo phiếu xuất >");
            Bill bill = new Bill();
            BillDetail billDetail = new BillDetail();
            try {
                // add bill
                System.out.print("Nhập mã code (B0001): ");
                bill.setBillCode(Console.scanner.nextLine());
                bill.setBillType(false);
                employeeService.showAll();
                System.out.print("Nhập mã nhân viên XUẤT: ");
                String empployeeId = Console.scanner.nextLine();
                bill.setEmployeeIdCreated(empployeeId);
                bill.setEmployeeIdAuth(empployeeId); // tạm thời lấy thằng này, khi cập nhật sẽ chọn lại
                bill.setCreated(new Date());
                bill.setAuthDate(new Date());
                bill.setBillStatus(0);
                billRepository.add(bill);

                // add bill detail
                List<Bill> lastBillId = billRepository.findAll(Bill.class);
                billDetail.setBillId(lastBillId.size());
                productService.showAll();
                System.out.print("Nhập Id sản phẩm (Product_Id): ");
                billDetail.setProductId(Console.scanner.nextLine());
                System.out.print("Nhập số lượng sản phẩm: ");
                billDetail.setQuantity(Integer.parseInt(Console.scanner.nextLine()));
                System.out.print("Nhập giá XUẤT: ");
                billDetail.setPrice(Float.parseFloat(Console.scanner.nextLine()));
                billDetailRepository.add(billDetail);

                System.out.println("Thêm phiếu Xuất thành công! Bạn có muốn nhập tiếp (Y:có - N:Không): ");
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
        long billIdResult = 0;
        Bill billToUpdate = null;
        BillDetail billDetailToUpdate = new BillDetail();

        System.out.println("< Cập nhật phiếu Xuất >");
        System.out.print("Nhập mã phiếu hoặc mã code cần cập nhật (Bill_id or Bill_code): ");
        String search = Console.scanner.nextLine();

        for (Bill b : billRepository.findAll(Bill.class)) {
            if ((String.valueOf(b.getBillId()).equals(search) || b.getBillCode().equals(search)) && !b.isBillType()) {
                billToUpdate = b;
                billIdResult = b.getBillId();
                Bill.showHeader();
                b.show();
                if (b.getBillStatus() == 2) {
                    System.out.println("Phiếu đang tìm kiếm đã được Duyệt, không cập nhật được!");
                    return;
                }
                break;
            }
        }

        if (billToUpdate == null) {
            System.out.println("Không tìm thấy phiếu Xuất có mã phiếu hoặc mã code: " + search);
            return;
        }

        try {
            System.out.println("Nhập thông tin cập nhật:");
            System.out.print("Nhập mã code mới (Enter nếu không muốn thay đổi): ");
            String newBillCode = Console.scanner.nextLine();
            if (!newBillCode.isEmpty()) {
                billToUpdate.setBillCode(newBillCode);
            }

            employeeService.showAll();
            System.out.print("Nhập mới mã nhân viên Xuất: ");
            String empployeeId = Console.scanner.nextLine();
            billToUpdate.setEmployeeIdCreated(empployeeId);

            billToUpdate.setCreated(new Date());

            System.out.print("Nhập trạng thái 0-Tạo 1-Hủy: ");
            billToUpdate.setBillStatus(Integer.parseInt(Console.scanner.nextLine()));

            for (BillDetail bd : billDetailRepository.findAll(BillDetail.class)) {
                if (bd.getBillId() == billIdResult) {
                    billDetailToUpdate = bd;
                    break;
                }
            }
            System.out.print("Nhập mã sản phẩm mới (Enter nếu không muốn thay đổi): ");
            String newProductId = Console.scanner.nextLine();
            if (!newProductId.isEmpty()) {
                billDetailToUpdate.setProductId(newProductId);
            }
            System.out.print("Nhập mới số lượng sản phẩm: ");
            billDetailToUpdate.setQuantity(Integer.parseInt(Console.scanner.nextLine()));
            System.out.print("Nhập mới giá XUẤT: ");
            billDetailToUpdate.setPrice(Float.parseFloat(Console.scanner.nextLine()));

            billRepository.edit(billToUpdate);
            billDetailRepository.edit(billDetailToUpdate);
            System.out.println("Cập nhật phiếu thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void approve() {
        long billIdResult = 0;
        Bill billToApprove = null;
        BillDetail billDetailToApprove = new BillDetail();

        System.out.print("Nhập mã phiếu hoặc mã code cần Duyệt (Bill_id or Bill_code): ");
        String search = Console.scanner.nextLine();

        for (Bill b : billRepository.findAll(Bill.class)) {
            if ((String.valueOf(b.getBillId()).equals(search) || b.getBillCode().equals(search)) && !b.isBillType()) {
                billToApprove = b;
                billIdResult = b.getBillId();
                Bill.showHeader();
                b.show();
                if (b.getBillStatus() == 2) {
                    System.out.println("Phiếu đang tìm kiếm đã được Duyệt!");
                    return;
                }
                break;
            }
        }
        if (billToApprove == null) {
            System.out.println("Không tìm thấy phiếu XUẤT có mã phiếu hoặc mã code: " + search);
            return;
        }

        try {
            employeeService.showAll();
            System.out.print("Nhập mã nhân viên Duyệt: ");
            billToApprove.setEmployeeIdAuth(Console.scanner.nextLine());
            billToApprove.setAuthDate(new Date());
            System.out.print("Bạn có muốn duyệt phiếu: 2-Duyệt   3-Thoát: ");
            int intStatus = Integer.parseInt(Console.scanner.nextLine());
            if (intStatus != 2){
                return;
            }
            billToApprove.setBillStatus(2);

            for (BillDetail bd : billDetailRepository.findAll(BillDetail.class)) {
                if (bd.getBillId() == billIdResult) {
                    billDetailToApprove = bd;
                    break;
                }
            }
            Product productSetQuantity = productService.findId(billDetailToApprove.getProductId());
            int newQuantity = productSetQuantity.getQuantity() - billDetailToApprove.getQuantity();
            if (newQuantity<0){
                System.out.println("Số lượng xuất vượt quá số lượng trong kho, hãy cập nhật số lượng SP");
                return;
            }
            productSetQuantity.setQuantity(newQuantity);
            billRepository.edit(billToApprove);
            productRepository.edit(productSetQuantity);
            System.out.println("Duyệt phiếu thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void findByIdOrCode() {
        int count = 0;
        System.out.print("Nhập mã phiếu hoặc mã code cần tìm (Bill_id or Bill_code): ");
        String search = Console.scanner.nextLine().toLowerCase();
        Bill.showHeader();
        for (Bill b : billRepository.findAll(Bill.class)) {
            if (((String.valueOf(b.getBillId())).toLowerCase().contains(search) || b.getBillCode().toLowerCase().contains(search)) && !b.isBillType()) {
                b.show();
                count++;
            }
        }
        if (count ==0){
            System.out.print("Không tìm thấy phiếu xuất với từ khóa: "+ search);
        }
    }
}
