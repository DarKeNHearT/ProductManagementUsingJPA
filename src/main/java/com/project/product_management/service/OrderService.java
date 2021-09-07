package com.project.product_management.service;

import com.project.product_management.model.Orders;
import com.project.product_management.model.customer;
import com.project.product_management.model.product;
import com.project.product_management.pojo.*;

import javassist.NotFoundException;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    OrderPojo saveit(OrderPojo orderPojo) throws Exception;

    Orders checkit(Long id) throws NotFoundException;

    ResponseEntity<ProductPrice> getproductsale(long productId) throws Exception;

    ResponseEntity<TotalPurchaseWithId> getTotalPurchase(long customerId) throws Exception;

    ResponseEntity<OutputDate> SelectDate(DatetimeSelect datetimeSelect) throws Exception;

    List<customer> getAllCustomer() throws Exception;

    List<product> getAllProduct() throws Exception;

    List<ProductPrice> getProductSale(long id) throws Exception;

    List<TotalPurchaseWithId> gettotalpurchase(long id) throws Exception;

    List<OutputDate> selectdate(DatetimeSelect datetimeSelect) throws Exception;

}
