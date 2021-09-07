package com.project.product_management.pojo;

public class OrderPojo {

    private Long order_id;
    private String customer;
    private String product;
    private long quantity;


    public Long getOrder_id() {
        return order_id;
    }


    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

}
