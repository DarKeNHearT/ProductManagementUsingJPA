package com.project.product_management.repository;


import com.project.product_management.model.Orders;
import com.project.product_management.pojo.*;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.Map;

@Repository
public interface repositoryOrder extends JpaRepository<Orders, Long> {

    @Query("SELECT new com.project.product_management.pojo.Totalprice(o.quantity,p.product_id,o.total_price) FROM Orders o JOIN o.product p WHERE p.product_id =:n")
    List<Totalprice> getTotalSales(@Param("n") Long id);

    @Query("SELECT new com.project.product_management.pojo.TotalPurchase(o.total_price,p.product_name) FROM Orders o JOIN o.customer c JOIN o.product p WHERE c.customer_id= :n")
    List<TotalPurchase> getTotalPurchases(@Param("n") Long id);

    @Query("SELECT new com.project.product_management.pojo.ListOfProduct(p.product_id) FROM Orders o JOIN o.product p Join o.customer c WHERE c.customer_id = :n")
    List<ListOfProduct> listOfOrderProduct(@Param("n") Long id);

    @Query("SELECT new com.project.product_management.pojo.OutputDate(o.quantity,o.total_price) FROM Orders o WHERE o.datetime BETWEEN  :a  and  :b ")
    List<OutputDate> listOfOrderBetweenDate(@Param("a")Date from , @Param("b")Date to);

    @Query(value = "SELECT o.order_id,c.customer_name,p.product_name,o.quantity,o.total_price,o.datetime from Orders o INNER JOIN customer c on c.customer_id = o.customer_id INNER JOIN product p on p.product_id = o.product_id",nativeQuery = true)
    List<Map<String,Object>> getAllOrder();

    @Query(value="SELECT * from Orders WHERE customer_id = :n",nativeQuery = true)
    List<Orders> getOrders(@Param("n") Long id);

    @Query(value="SELECT * from Orders WHERE product_id = :n",nativeQuery = true)
    List<Orders> getOrder(@Param("n") Long id);

    @Query(value="SELECT * from Orders WHERE order_id = :n",nativeQuery = true)
    Orders getOrderById(@Param("n") Long id);

    @Query(value="SELECT * from Orders",nativeQuery = true)
    List<Orders> getAllOrderDefault();
}
