package com.project.product_management.pojo;

public class OutputDate {
    private long quantity;
    private Long total_price;


    public OutputDate(long quantity, Long total_price) {
        this.quantity = quantity;
        this.total_price = total_price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public OutputDate() {
    }

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }
}
