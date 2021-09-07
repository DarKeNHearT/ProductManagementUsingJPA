package com.project.product_management.model;

import javax.persistence.*;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "orders")
public class Orders {
    static long millis = System.currentTimeMillis();
    static LocalDate localDate = LocalDate.now();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    public Long order_id;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id")
    private com.project.product_management.model.customer customer;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinColumn(name ="product_id")
    private com.project.product_management.model.product product;

    @Column(name = "quantity")
    private long quantity;

    @Column(name = "total_price")
    private Long total_price;

    @Column(name = "datetime")
    private Date datetime = new Date(millis);



    public Long getOrder_id() {
        return order_id;
    }

    public void setOrder_id(long order_id) {
        this.order_id = order_id;
    }

    public com.project.product_management.model.product getProduct() {
        return product;
    }

    public void setProduct(com.project.product_management.model.product product) {
        this.product = product;
    }

    public com.project.product_management.model.customer getCustomerId() {
        return customer;
    }

    public void setCustomerId(com.project.product_management.model.customer customerId) {
        this.customer = customerId;
    }

    public Orders() {
    }

    public Orders(Long order_id, com.project.product_management.model.customer customer, com.project.product_management.model.product product, long quantity, Long total_price, Date datetime) {
        this.order_id = order_id;
        this.customer = customer;
        this.product = product;
        this.quantity = quantity;
        this.total_price = total_price;
        this.datetime = datetime;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public Long getTotal_price() {
        return total_price;
    }

    public void setTotal_price(Long total_price) {
        this.total_price = total_price;
    }

    public Date getDate() {
        return datetime;
    }

    public void setDate(Date date) {
        this.datetime = date;
    }
}

