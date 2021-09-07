package com.project.product_management.pojo;

public class TotalPurchase {
    private long total_purchase;
    private String name;


    public long getTotal_purchase() {
        return total_purchase;
    }

    public void setTotal_purchase(long total_purchase) {
        this.total_purchase = total_purchase;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TotalPurchase(long total_purchase, String name) {
        this.total_purchase = total_purchase;
        this.name = name;
    }

    public TotalPurchase() {
    }
}
