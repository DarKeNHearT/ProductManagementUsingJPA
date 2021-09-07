package com.project.product_management.repository;


import com.project.product_management.model.customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface repositoryCustomer extends JpaRepository<customer, Long> {

    @Query("SELECT c.customer_id FROM customer c WHERE c.customer_name = :n")
    public Long getUserIdByName(@Param("n") String name);

    @Query("SELECT c.customer_name FROM customer c WHERE c.customer_id = :n")
    public String getNameById(@Param("n") Long id);

    @Query(value = "SELECT c.* from customer c order by c.customer_id",nativeQuery = true)
    public List<customer> getAllCustomer();

    @Query("SELECT c.email FROM customer c WHERE c.customer_id = :n")
    public String getEmailById(@Param("n") Long id);


}
