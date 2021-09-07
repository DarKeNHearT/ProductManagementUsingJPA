package com.project.product_management.controller;

import com.project.product_management.email.EmailService;
import com.project.product_management.email.EmailTemplate;
import com.project.product_management.model.Orders;
import com.project.product_management.pojo.*;
import com.project.product_management.service.OrderService;
import com.project.product_management.service.impl.OrderServiceImpl;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.project.product_management.repository.repositoryCustomer;
import com.project.product_management.repository.repositoryProduct;
import com.project.product_management.repository.repositoryOrder;
import com.project.product_management.model.customer;
import com.project.product_management.model.product;
import com.project.product_management.exceptionhandling.valueNotFound;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.project.product_management.pojo.DatetimeSelect;

import javax.validation.Valid;
import java.sql.Date;
import java.util.*;


@org.springframework.stereotype.Controller
@RequestMapping("/api/form/")
public class Controller {




    @Autowired
    private repositoryCustomer repositoryCustomers;

    @Autowired
    private repositoryProduct repositoryProducts;

    @Autowired
    private repositoryOrder repositoryOrders;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderServiceImpl orderServices;

    @Autowired
    private EmailService emailService;


    @Transactional
    @PostMapping("/customer")
    public String createCustomerByForm(@Valid @ModelAttribute("customers") customer Customers,
                                       BindingResult  bindingResult) throws Exception{
        if(bindingResult.hasErrors()){
            return "AddCustomer";
        }
        else {
            if (Customers.getCustomer_name() == "")
                throw new Exception("Name cannot Be Empty");
            repositoryCustomers.save(Customers);
            EmailTemplate emailTemplate = new EmailTemplate();
            emailTemplate.setSendto(repositoryCustomers.getEmailById(Customers.getCustomer_id()));
            emailTemplate.setBody("Account Has Been Add");
            emailTemplate.setSubject("Added");

            emailService.sendTextEmail(emailTemplate);
            return "redirect:/Customer";
        }
    }

    @Transactional
    @PostMapping("/product")
    public String createProductByForm(@ModelAttribute("products")product products,@RequestParam("file")MultipartFile file) throws Exception{
        if(products.getProduct_name()=="")
            throw new Exception("Name cannot Be Empty");
        if(products.getProduct_price()==0)
            throw new Exception("Price cannot Be Empty");

        orderServices.SaveProduct(file ,products.getProduct_name(),products.getProduct_id(),products.getProduct_price());
        return "redirect:/Product/display";
    }

    @Transactional
    @PostMapping("/order")
    public String createOrderByForm(@ModelAttribute("orders") OrderPojo orderPojo) throws Exception{
        Orders o = new Orders();
        long price = 0;
        Long customer_id = repositoryCustomers.getUserIdByName(orderPojo.getCustomer());
        Long product_id = repositoryProducts.getUserIdByName(orderPojo.getProduct());
        if(customer_id==null || product_id==null)
            throw new Exception("Either Product or Customer Not found");
        o.setQuantity(orderPojo.getQuantity());
        if (orderPojo.getOrder_id() != null) {
            o = new Orders();
            o.setOrder_id(orderPojo.getOrder_id());
        }
        price += o.getQuantity()*repositoryProducts.getPriceById(product_id);
        o.setTotal_price(price);

        List<ListOfProduct> check = repositoryOrders.listOfOrderProduct(customer_id);
        for(int i =0;i<check.size();i++){
            if(check.get(i).getProduct_id() == product_id)
                throw new Exception("Product already Present For Given Customer!!!");

        }

        Optional<product> pr = repositoryProducts.findById(product_id);

        Optional<customer> hr = repositoryCustomers.findById(customer_id);

        o.setProduct(pr.get());
        o.setCustomerId(hr.get());
        repositoryOrders.save(o);
        return "redirect:/Order/display";
    }

}



