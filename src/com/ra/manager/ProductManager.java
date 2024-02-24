package com.ra.manager;

import com.ra.entity.Product;
import com.ra.repository.impl.Repository;
import com.ra.service.ProductService;
import com.ra.service.impl.ProductServiceImpl;
import com.ra.util.Console;
import com.ra.util.DateFomat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ProductManager implements Manager{
    private ProductService productService;

    public ProductManager(){
        this.productService = new ProductServiceImpl();
    }

    @Override
    public void run() {

        do {
            System.out.println("***************PRODUCT MANAGEMENT***************");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm mới sản phẩm");
            System.out.println("3. Cập nhật sản phẩm");
            System.out.println("4. Tìm kiếm sản phẩm");
            System.out.println("5. Cập nhật trạng thái sản phẩm");
            System.out.println("6. Thoát");
            System.out.print("Chọn chức năng: ");
            int choose = Integer.parseInt(Console.scanner.nextLine());
            switch (choose){
                case 1:
                    Product.showHeader();
                    for (Product p : productService.findAll()){
                        try {
                            p.show();
                        } catch (Exception ex){
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("==============================================================");
                    break;
                case 2:
                    break;
                case 3:
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Hãy nhập từ 1-6");
            }

        } while (true);
    }
}
