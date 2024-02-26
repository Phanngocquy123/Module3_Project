package com.ra.service.impl;

import com.ra.entity.Product;
import com.ra.repository.impl.Repository;
import com.ra.service.ProductService;
import com.ra.util.Console;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private Repository<Product, String> productRepository;

    public ProductServiceImpl() {
        this.productRepository = new Repository<>();
    }


    @Override
    public void showAll(){
        int currentPage = 1;
        final int pageSize = 10;
        List<Product> productList = productRepository.findAll(Product.class);
        int pageTotal = (int) Math.ceil((double) productList.size() / pageSize);
        tenList(currentPage, pageTotal, pageSize,productList);
    }


    @Override
    public Product findId(String id) {
        return this.productRepository.findId(id, Product.class);
    }

    @Override
    public void add() {
        do {
            System.out.println("< Thêm sản phẩm >");
            Product newProduct = new Product();
            try {
                System.out.print("Nhập Id sản phẩm: ");
                newProduct.setProductId(Console.scanner.nextLine());
                System.out.print("Nhập tên sản phẩm: ");
                newProduct.setProductName(Console.scanner.nextLine());
                System.out.print("Nhập nhà sản xuất: ");
                newProduct.setManufacturer(Console.scanner.nextLine());
                newProduct.setCreated(new Date());
                System.out.print("Nhập lô chứa sản phẩm: ");
                newProduct.setBatch(Integer.parseInt(Console.scanner.nextLine()));
                newProduct.setQuantity(0);
                newProduct.setProductStatus(true);
                productRepository.add(newProduct);
                System.out.println("Thêm sản phẩm thành công! Bạn có muốn nhập tiếp (Y:có - N:Không): ");
                String choiceNext = Console.scanner.nextLine();
                if (choiceNext.equalsIgnoreCase("n")) {
                    return;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } while (true);
    }

    @Override
    public void update() {
        System.out.println("< Cập nhật sản phẩm >");
        System.out.print("Nhập Id sản phẩm muốn cập nhật: ");
        String idUpdate = Console.scanner.nextLine();
        Product updateProduct = new Product();
        if (findId(idUpdate) != null) {
            try {
                updateProduct.setProductId(idUpdate);
                System.out.print("Nhập tên sản phẩm: ");
                updateProduct.setProductName(Console.scanner.nextLine());
                System.out.print("Nhập nhà sản xuất: ");
                updateProduct.setManufacturer(Console.scanner.nextLine());
                updateProduct.setCreated(new Date());
                System.out.print("Nhập lô chứa sản phẩm: ");
                updateProduct.setBatch(Integer.parseInt(Console.scanner.nextLine()));
                //updateProduct.setQuantity(0);
                updateProduct.setProductStatus(true);
                productRepository.edit(updateProduct);
                System.out.println("Cập nhật sản phẩm thành công!");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else
            System.out.println("Không tìm thấy ID - " + idUpdate);
    }

    @Override
    public void findByName() {
        final int pageSize = 10;
        int currentPage = 1;

        System.out.print("Nhập tên sản phẩm muốn tìm kiếm: ");
        String searchName = Console.scanner.nextLine();
        List<Product> productsFound = new ArrayList<>();
        for (Product p : productRepository.findAll(Product.class)){
            if (p.getProductName().toLowerCase().contains(searchName.toLowerCase())) {
                productsFound.add(p);
            }
        }
        int pageTotal = (int) Math.ceil((double) productsFound.size() / pageSize); // Math.ceil(double a) làm tròn lên
        tenList(currentPage, pageTotal, pageSize, productsFound);

    }

    @Override
    public void updateStatus() {
        System.out.println("< Cập nhật trạng thái sản phẩm >");
        System.out.print("Nhập Id sản phẩm muốn cập nhật trạng thái: ");
        String idUpdateStatus = Console.scanner.nextLine();
        Product newProStatus = findId(idUpdateStatus);
        if (newProStatus != null) {
            System.out.printf("Trạng thái hiện tại của sản phẩm (Id: %s) là: %s \n",
                    idUpdateStatus, newProStatus.isProductStatus() ? "Hoạt động" : "Không hoạt động");
            System.out.print("Nhập trạng thái bạn muốn cập nhật (true - Hoạt động || false - Không hoạt động): ");
            boolean status = Boolean.parseBoolean(Console.scanner.nextLine());

            newProStatus.setProductStatus(status);
            productRepository.edit(newProStatus);
            System.out.println("Cập nhật trạng thái sản phẩm Id - " + idUpdateStatus + " thành công");
        } else
            System.out.println("Không tìm thấy ID - " + idUpdateStatus);
    }
    public void tenList(int currentPage, int pageToal, int pageSize, List<Product> product){
        do {
            int startIndex = (currentPage - 1) * pageSize;
            int endIndex = currentPage * pageSize;

            Product.showHeader();
            for (int i = startIndex; i < Math.min(endIndex, product.size()); i++) {
                product.get(i).show();
            }
            System.out.printf("Trang: %d/%d\n", currentPage, pageToal);
            System.out.println("<--(1) Trang trước __ Trang sau (2)-->");
            System.out.println("(3) Trở về menu PRODUCT MANAGEMENT");
            System.out.print("Nhập lựa chọn: ");
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
