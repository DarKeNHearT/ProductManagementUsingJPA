package com.project.product_management.pojo;

public class OrderWithId {
    private Long order_id;
    private long customer_id;
    private long product_id;
    private Long total_price;
    private long quantity;

    public OrderWithId(Long order_id, long customer_id, long product_id, Long total_price, long quantity) {
        this.order_id = order_id;
        this.customer_id = customer_id;
        this.product_id = product_id;
        this.total_price = total_price;
        this.quantity = quantity;
    }

    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Long order_id) {
        this.order_id = order_id;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(long product_id) {
        this.product_id = product_id;
    }

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
