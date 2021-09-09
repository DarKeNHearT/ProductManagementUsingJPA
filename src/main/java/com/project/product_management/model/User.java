package com.project.product_management.model;

import javax.persistence.*;

@Entity
@Table(name ="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "UserName")
    private String userName;

    @Column(name = "Passwords")
    private String password;

    @Column(name = "Roles")
    private String roles;

    public User() {
    }

    @Column(name = "Active")
    private Boolean active;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public User(Long id, String userName, String password, String roles, Boolean active) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.active = active;
    }
}
