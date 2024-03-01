package com.ra.entity;

import com.ra.exception.InputException;
import com.ra.util.Column;
import com.ra.util.Id;
import com.ra.util.Table;

import java.text.SimpleDateFormat;

@Table(name = "Bill_detail")
public class BillDetail {
    @Id(autoIncrement = true)
    @Column(name = "Bill_Detail_Id")
    private long billDetailId;
    @Column(name = "Bill_Id")
    private long billId;
    @Column(name = "Product_Id")
    private String productId;
    @Column(name = "Quantity")
    private int quantity;
    @Column(name = "Price")
    private float price;

    public BillDetail() {
    }

    public BillDetail(long billId, String productId, int quantity, float price) {
        this.billId = billId;
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
    }

    public long getBillDetailId() {
        return billDetailId;
    }

    public long getBillId() {
        return billId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public void setBillDetailId(long billDetailId) {
        this.billDetailId = billDetailId;
    }

    public void setBillId(long billId) throws Exception{
        if (billId < 0)
            throw new InputException("-> Mã phiếu nhập/xuát không được bỏ trống");
        this.billId = billId;
    }

    public void setProductId(String productId) throws Exception{
        if (productId.length() != 5)
            throw new InputException("-> Mã sản phẩm phải gồm 5 ký tự");
        this.productId = productId;
    }

    public void setQuantity(int quantity) throws Exception{
        if (quantity <= 0)
            throw new InputException("-> Số lượng nhập/xuất > 0");
        this.quantity = quantity;
    }

    public void setPrice(float price) throws Exception{
        if (price <= 0)
            throw new InputException("-> Giá nhập/xuất > 0");
        this.price = price;
    }

    public static void showHeader(){
        System.out.println("===========================================DANH SÁCH CHI TIẾT PHIẾU NHẬP===============================================");
        System.out.println("| Bill_Detail_Id| Bill_Id| Product_Id| Quantity| Price");  //bill_id mã phiếu nhập/ xuất
    }
    public void show(){
        System.out.printf("| %-17d| %-7d| %-10s| %-8d| %f\n",
               this.billDetailId, this.billId,this.productId,this.quantity, this.price);
    }
}
