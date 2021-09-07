package com.project.product_management.model;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Entity
@Table(name = "customer")
public class customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "customer_id")
    private long customer_id;


    @NotNull
    @Size(min = 2, message = "First Name should have atleast 2 characters")
    @Column(name = "customer_name")
    private String customer_name;

    @OneToMany(mappedBy = "customer")
    private List<Orders> orderList;

    @Email
    @Column(name = "email")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public customer() {
    }

    public customer(String customer_name, String email) {
        this.customer_name = customer_name;
        this.email = email;
    }

    public long getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(long customer_id) {
        this.customer_id = customer_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }
}
