package com.project.product_management.pojo;

public class Totalprice {
    private Long quantity;
    private Long product_id;
    private Long total_price;

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }

    public Totalprice(Long quantity, Long product_id, Long total_price) {
        this.quantity = quantity;
        this.product_id = product_id;
        this.total_price = total_price;
    }

    public Long getProduct_id() {
        return product_id;
    }

    public Totalprice() {
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }
}
