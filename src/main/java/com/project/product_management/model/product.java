package com.project.product_management.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long product_id;

    @Column(name = "product_name")
    private String product_name;

    @Column(name = "product_price")
    private long product_price;

    @Column(name = "Image")
    private String image;

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    private String filename;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @OneToMany(mappedBy = "product")
    private List<Orders> orderList;

    public product(Long product_id, String product_name, long product_price, String image) {
        this.product_id = product_id;
        this.product_name = product_name;
        this.product_price = product_price;
        this.image = image;
    }

    public product() {
    }

    public Long getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Long product_id) {
        this.product_id = product_id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public long getProduct_price() {
        return product_price;
    }

    public void setProduct_price(long product_price) {
        this.product_price = product_price;
    }

}
