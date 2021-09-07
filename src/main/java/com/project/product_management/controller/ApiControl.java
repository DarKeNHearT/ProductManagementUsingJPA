package com.project.product_management.controller;

import com.project.product_management.exceptionhandling.valueNotFound;
import com.project.product_management.model.Orders;
import com.project.product_management.model.customer;
import com.project.product_management.model.product;
import com.project.product_management.pojo.*;
import com.project.product_management.repository.repositoryCustomer;
import com.project.product_management.repository.repositoryOrder;
import com.project.product_management.repository.repositoryProduct;
import com.project.product_management.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/basic/")
public class ApiControl {
    @Autowired
    private repositoryCustomer repositoryCustomers;

    @Autowired
    private repositoryProduct repositoryProducts;

    @Autowired
    private repositoryOrder repositoryOrders;

    @Autowired
    private OrderService orderService;

    @Transactional
    @PostMapping("customer")
    public customer createCustomer(@RequestBody customer customers) throws Exception {
        if(customers.getCustomer_name()==null)
            throw new Exception("Customer Name Cannot Be Null");
        return this.repositoryCustomers.save(customers);
    }
    //get customer
    @GetMapping("customer")
    public List<customer> getAllCustomers() throws Exception {

        return orderService.getAllCustomer();
    }

    //get product
    @GetMapping("product")
    public List<product> getAllProducts() throws Exception {
        return orderService.getAllProduct();
    }


    @GetMapping("order")
    public List<Map<String,Object>> getAllOrders() throws Exception {
        return repositoryOrders.getAllOrder();
    }

    /*Get By Id*/

    //get customer
    @GetMapping("customer/{id}")
    public ResponseEntity<customer> getCustomerById(@PathVariable(value = "id") long customerId)
            throws valueNotFound {
        customer customers = repositoryCustomers.findById(customerId)
                .orElseThrow(() -> new valueNotFound("Customer Not found for this Id: " + customerId));
        return ResponseEntity.ok().body(customers);
    }

    //get product
    @GetMapping("product/{id}")
    public ResponseEntity<product> getProductById(@PathVariable(value = "id") long productId)
            throws valueNotFound {
        product products = repositoryProducts.findById(productId)
                .orElseThrow(() -> new valueNotFound("Product Not found For this Id: " + productId));
        return ResponseEntity.ok().body(products);
    }

    //get order
    @GetMapping("order/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable(value = "id") long orderId)
            throws valueNotFound {
        Orders orders = repositoryOrders.findById(orderId)
                .orElseThrow(() -> new valueNotFound("Order Not found For this Id: " + orderId));
        return ResponseEntity.ok().body(orders);
    }

    //SAVE

    //post customer



    //post product
    @Transactional
    @PostMapping("product")
    public product createProduct(@RequestBody product products) throws Exception {
        if(products.getProduct_name()==null || products.getProduct_price()== 0)
            throw new Exception("Product Name And Product Price Cannot be NULL");
        return this.repositoryProducts.save(products);
    }

    @PostMapping("order")
    public OrderPojo createOrder(@RequestBody OrderPojo orderPojo) throws Exception {
        return orderService.saveit(orderPojo);
    }

    //Update

    //customers
    @PutMapping("customer/{id}")
    public ResponseEntity<customer> updateCustomer(@PathVariable(value = "id") long customerId, @RequestBody customer customerDetails)
            throws Exception {
        customer customers = repositoryCustomers.findById(customerId)
                .orElseThrow(() -> new valueNotFound("Customer Not found For this Id: " + customerId));
        if(customerDetails.getCustomer_name()==null)
            throw new Exception("Customer Name cannot Be Null");
        customers.setCustomer_name(customerDetails.getCustomer_name());
        customers.setEmail(customerDetails.getEmail());
        this.repositoryCustomers.save(customers);
        return ResponseEntity.ok().body(customers);
    }

    //product
    @PutMapping("product/{id}")
    public ResponseEntity<product> updateProduct(@PathVariable(value = "id")long productId, @RequestBody product productDetails)
            throws Exception{
        product products = repositoryProducts.findById(productId)
                .orElseThrow(() -> new valueNotFound("Product Not found for this Id: "+productId));
        if(products.getProduct_price()==0 || products.getProduct_name()==null)
            throw new Exception("Product Name And Product Price Cannot be NULL");
        products.setProduct_price(productDetails.getProduct_price());
        products.setProduct_name(productDetails.getProduct_name());
        this.repositoryProducts.save(products);
        return ResponseEntity.ok().body(products);
    }

    //order
    @PutMapping("order/{id}")
    public ResponseEntity<Orders> updateProduct(@PathVariable(value = "id")long orderId, @RequestBody OrderPojo orderDetails)
            throws Exception {
        Optional<Orders> hr = repositoryOrders.findById(orderId);
        if(!hr.isPresent()) {
            throw new Exception("Order not found");
        }
        Orders orders = new Orders();
        Optional<product> pr = repositoryProducts.findById(repositoryProducts.getUserIdByName(orderDetails.getProduct()));
        if(!pr.isPresent()){
            throw new Exception("product not found");
        }
        Optional<customer> cu = repositoryCustomers.findById(repositoryCustomers.getUserIdByName(orderDetails.getCustomer()));
        if(!cu.isPresent()){
            throw new Exception("Customer No found");
        }
        orders.setProduct(pr.get());
        orders.setCustomerId(cu.get());
        Long price = repositoryProducts.getPriceById(repositoryProducts.getUserIdByName(orderDetails.getProduct()));
        orders.setTotal_price(price);
        orders.setQuantity(orderDetails.getQuantity());
        orders.setOrder_id(orderId);
        this.repositoryOrders.save(orders);
        return ResponseEntity.ok().body(orders);
    }


    //delete

    //customer
    @DeleteMapping("customer/{id}")
    public Map<String,Boolean> deleteCustomer(@PathVariable(value = "id")long customerId)
            throws valueNotFound{
        customer customers = repositoryCustomers.findById(customerId)
                .orElseThrow(()-> new valueNotFound("Customer Not found For this Id: "+customerId));
        this.repositoryCustomers.delete(customers);
        Map<String, Boolean>  response = new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return response;
    }
    //product
    @DeleteMapping("product/{id}")
    public Map<String,Boolean> deleteProduct(@PathVariable(value = "id")long productId)
            throws valueNotFound{
        product products = repositoryProducts.findById(productId)
                .orElseThrow(()-> new valueNotFound("Product Not found For this Id: "+productId));
        this.repositoryProducts.delete(products);
        Map<String, Boolean>  response = new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return response;
    }

    @DeleteMapping("order/{id}")
    public Map<String,Boolean> deleteOrder(@PathVariable(value = "id")long orderId)
            throws valueNotFound{
        Orders orders = repositoryOrders.findById(orderId)
                .orElseThrow(()-> new valueNotFound("Order Not found For this Id: "+orderId));
        this.repositoryOrders.delete(orders);
        Map<String, Boolean>  response = new HashMap<>();
        response.put("Deleted",Boolean.TRUE);
        return response;
    }

    @GetMapping("order/product/{id}")
    public ResponseEntity<ProductPrice> totalSales(@PathVariable(value = "id")long id) throws Exception {
        return orderService.getproductsale(id);
    }

    @GetMapping("order/customer/{id}")
    public ResponseEntity<TotalPurchaseWithId> totalPurchase(@PathVariable(value = "id") long id ) throws Exception{
        return orderService.getTotalPurchase(id);
    }

    @GetMapping("datetime")
    public ResponseEntity<OutputDate> selectDate(@RequestBody DatetimeSelect datetimeSelect)throws Exception{
        return orderService.SelectDate(datetimeSelect);
    }





}
