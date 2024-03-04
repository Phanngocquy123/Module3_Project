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
import java.text.SimpleDateFormat;
import java.util.Date;
import java.sql.ResultSet;
import java.util.List;


public class ReceiptServiceImpl implements GoodsSlipService {
    private Repository<Bill, Long> receiptRepository;
    private Repository<BillDetail, Long> receiptDetailRepository;
    private Repository<Employee, String> employeeRepository;
    private Repository<Product, String> productRepository;

    EmployeeServiceImpl employeeService = new EmployeeServiceImpl();
    ProductServiceImpl productService = new ProductServiceImpl();

    public ReceiptServiceImpl(Repository<Employee, String> employeeRepository, Repository<Product, String> productRepository) {
        this.receiptRepository = new Repository<>();
        this.receiptDetailRepository = new Repository<>();
        this.employeeRepository = employeeRepository;
        this.productRepository = productRepository;
    }

    public ReceiptServiceImpl() {
        this.receiptRepository = new Repository<>();
        this.receiptDetailRepository = new Repository<>();
    }


    @Override
    public void findAll() {
        Bill.showHeader();
        for (Bill b : receiptRepository.findAll(Bill.class)) {
            if (b.isBillType()) {
                b.show();
            }
        }
    }

    @Override
    public void findAllDetail() {
        Connection conn = null;
        System.out.print("Nhập mã phiếu hoặc mã code cần xem chi tiết (Bill_id or Bill_code): ");
        String search = Console.scanner.nextLine();
        System.out.println("=============================CHI TIẾT PHIẾU NHẬP=============================");
        System.out.println("| Bill_Detail_Id| Bill_Id| Bill_code| Product_Id| Quantity| Price  | Created");
        try {
            conn = MySqlConnect.open();
            CallableStatement cs = conn.prepareCall("{call find_detail_receipt()}");
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                Long billDetailId = rs.getLong("Bill_Detail_Id");
                Long billId = rs.getLong("Bill_Id");
                String productId = rs.getString("Product_Id");
                int quantity = rs.getInt("Quantity");
                float price = rs.getFloat("Price");
                String formattedPrice = String.format("%.2f", price);
                String billCode = rs.getString("Bill_Code");
                Date createdDate = rs.getDate("Created");
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String formattedDate = dateFormat.format(createdDate);

                if ((String.valueOf(billId).equals(search) || billCode.equals(search))) {
                    System.out.printf("| %-14d| %-7d| %-9s| %-10s| %-8d| %-7s| %s\n",
                            billDetailId, billId, billCode, productId, quantity, formattedPrice, formattedDate);

                }

            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            MySqlConnect.close(conn);
        }
    }

    @Override
    public void add(String empIdAcc) {
        do {
            System.out.println("< Tạo phiếu nhập >");
            Bill bill = new Bill();
            BillDetail billDetail = new BillDetail();
            try {

                // add bill
                System.out.print("Nhập mã code: ");
                bill.setBillCode(Console.scanner.nextLine());
                bill.setBillType(true);

                if (empIdAcc == null) {                 // check admin hay user
                    employeeService.showAll();
                    System.out.print("Nhập mã nhân viên nhập: ");
                    String empployeeId = Console.scanner.nextLine();
                    bill.setEmployeeIdCreated(empployeeId);
                    bill.setEmployeeIdAuth(empployeeId); // tạm thời lấy thằng này, khi cập nhật sẽ chọn lại
                } else {
                    bill.setEmployeeIdCreated(empIdAcc);
                    bill.setEmployeeIdAuth(empIdAcc);
                }
                bill.setCreated(new Date());
                bill.setAuthDate(new Date());
                bill.setBillStatus(0);
                receiptRepository.add(bill);

                // add bill detail
                List<Bill> lastBillId = receiptRepository.findAll(Bill.class);
                billDetail.setBillId(lastBillId.size());
                productService.showAll();
                System.out.print("Nhập Id sản phẩm (Product_Id): ");
                billDetail.setProductId(Console.scanner.nextLine());
                System.out.print("Nhập số lượng sản phẩm: ");
                billDetail.setQuantity(Integer.parseInt(Console.scanner.nextLine()));
                System.out.print("Nhập giá Nhập: ");
                billDetail.setPrice(Float.parseFloat(Console.scanner.nextLine()));

                receiptDetailRepository.add(billDetail);

                System.out.println("Thêm phiếu nhập thành công! Bạn có muốn nhập tiếp (Y:có - N:Không): ");
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
    public void update(String empIdAcc) {
        long billIdResult = 0;
        Bill billToUpdate = null;
        BillDetail billDetailToUpdate = new BillDetail();

        System.out.println("< Cập nhật phiếu nhập >");
        System.out.print("Nhập mã phiếu hoặc mã code cần cập nhật (Bill_id or Bill_code): ");
        String search = Console.scanner.nextLine();

        for (Bill b : receiptRepository.findAll(Bill.class)) {
            if ((String.valueOf(b.getBillId()).equals(search) || b.getBillCode().equals(search)) && b.isBillType()) {
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
            System.out.println("Không tìm thấy phiếu nhập có mã phiếu hoặc mã code: " + search);
            return;
        }

        try {
            System.out.println("Nhập thông tin cập nhật:");
            System.out.print("Nhập mã code mới (Enter nếu không muốn thay đổi): ");
            String newBillCode = Console.scanner.nextLine();
            if (!newBillCode.isEmpty()) {
                billToUpdate.setBillCode(newBillCode);
            }

            if (empIdAcc == null) {
                employeeService.showAll();
                System.out.print("Nhập mới mã nhân viên nhập: ");
                String empployeeId = Console.scanner.nextLine();
                billToUpdate.setEmployeeIdCreated(empployeeId);
            } else {
                billToUpdate.setEmployeeIdCreated(empIdAcc);
            }


            billToUpdate.setCreated(new Date());

            System.out.print("Nhập trạng thái 0-Tạo 1-Hủy: ");
            billToUpdate.setBillStatus(Integer.parseInt(Console.scanner.nextLine()));

            for (BillDetail bd : receiptDetailRepository.findAll(BillDetail.class)) {
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
            System.out.print("Nhập mới giá Nhập: ");
            billDetailToUpdate.setPrice(Float.parseFloat(Console.scanner.nextLine()));

            receiptRepository.edit(billToUpdate);
            receiptDetailRepository.edit(billDetailToUpdate);
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

        for (Bill b : receiptRepository.findAll(Bill.class)) {
            if ((String.valueOf(b.getBillId()).equals(search) || b.getBillCode().equals(search)) && b.isBillType()) {
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
            System.out.println("Không tìm thấy phiếu nhập có mã phiếu hoặc mã code: " + search);
            return;
        }

        try {
            employeeService.showAll();
            System.out.print("Nhập mã nhân viên Duyệt: ");
            billToApprove.setEmployeeIdAuth(Console.scanner.nextLine());
            billToApprove.setAuthDate(new Date());
            System.out.print("Bạn có muốn duyệt phiếu: 2-Duyệt   3-Thoát: ");
            int intStatus = Integer.parseInt(Console.scanner.nextLine());
            if (intStatus != 2) {
                return;
            }
            billToApprove.setBillStatus(2);
            receiptRepository.edit(billToApprove);

            for (BillDetail bd : receiptDetailRepository.findAll(BillDetail.class)) {
                if (bd.getBillId() == billIdResult) {
                    billDetailToApprove = bd;
                    break;
                }
            }
            Product productSetQuantity = productService.findId(billDetailToApprove.getProductId());
            int newQuantity = productSetQuantity.getQuantity() + billDetailToApprove.getQuantity();
            productSetQuantity.setQuantity(newQuantity);
            productRepository.edit(productSetQuantity);
            System.out.println("Duyệt phiếu thành công!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void findByIdOrCode(String empIdAcc) {
        int count = 0;
        System.out.print("Nhập mã phiếu hoặc mã code cần tìm (Bill_id or Bill_code): ");
        String search = Console.scanner.nextLine().toLowerCase();
        List<Bill> listBill = receiptRepository.findAll(Bill.class);
        Bill.showHeader();
        if (empIdAcc == null) {
            for (Bill b : listBill) {
                if (((String.valueOf(b.getBillId())).toLowerCase().contains(search) || b.getBillCode().toLowerCase().contains(search)) && b.isBillType()) {
                    b.show();
                    count++;
                }
            }
        } else {
            for (Bill b : listBill) {
                if (((String.valueOf(b.getBillId())).toLowerCase().contains(search) || b.getBillCode().toLowerCase().contains(search)) && b.isBillType() && b.getEmployeeIdCreated().equals(empIdAcc)) {
                    b.show();
                    count++;
                }
            }
        }

        if (count == 0) {
            System.out.print("Không tìm thấy phiếu xuất với từ khóa: \n" + search);
        }
    }

    @Override
    public void listByStatus(String empIdAcc) {
        System.out.print("Nhập trạng thái muốn hiển thị  0-Tạo; 1-Hủy;  2-Duyệt:");
        int numberStatus = Integer.parseInt(Console.scanner.nextLine());
        Bill.showHeader();
        for (Bill b : receiptRepository.findAll(Bill.class)) {
            if (b.getBillStatus() == numberStatus && b.isBillType() && b.getEmployeeIdCreated().equals(empIdAcc)) {
                b.show();
            }
        }
    }
}
