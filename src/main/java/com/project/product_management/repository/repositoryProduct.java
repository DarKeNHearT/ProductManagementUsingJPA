package com.project.product_management.repository;

import com.project.product_management.model.customer;
import com.project.product_management.model.product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface repositoryProduct extends JpaRepository<product, Long> {

    @Query(value = "SELECT product_id FROM product  WHERE product_name = :n",nativeQuery = true)
    public Long getUserIdByName(@Param("n") String name);

    @Query("SELECT c.product_price FROM product c WHERE c.product_id = :n")
    public Long getPriceById(@Param("n")Long id);

    @Query("SELECT c.product_name FROM product c WHERE c.product_id = :n")
    public String getNameById(@Param("n") Long id);

    @Query(value = "SELECT p.* from product p order by p.product_id",nativeQuery = true)
    public List<product> getAllProduct();
}
