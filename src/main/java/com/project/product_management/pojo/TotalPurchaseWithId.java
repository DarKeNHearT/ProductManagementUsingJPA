package com.project.product_management.pojo;

import java.util.List;

public class TotalPurchaseWithId {
    private long total_purchase;
    private List<String> name;
    private long customer_id;
    private String customerName;

    public TotalPurchaseWithId(long total_purchase, List<String> name, long customer_id) {
        this.total_purchase = total_purchase;
        this.name = name;
        this.customer_id = customer_id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public long getTotal_purchase() {
        return total_purchase;
    }

    public void setTotal_purchase(long total_purchase) {
        this.total_purchase = total_purchase;
    }

    public TotalPurchaseWithId() {
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }
}