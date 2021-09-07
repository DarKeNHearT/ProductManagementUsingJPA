package com.project.product_management.pojo;

public class ProductPrice {
    private Long totalSales;
    private String name;
    private long productId;
    private Long totalPrice;

    public Long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ProductPrice(Long totalSales, String name, long productId, Long totalPrice) {
        this.totalSales = totalSales;
        this.name = name;
        this.productId = productId;
        this.totalPrice = totalPrice;
    }

    public ProductPrice() {
    }

    public long getTotalSales() {
        return totalSales;
    }

    public void setTotalSales(Long totalSales) {
        this.totalSales = totalSales;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }
}
