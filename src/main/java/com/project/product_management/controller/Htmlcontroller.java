package com.project.product_management.controller;

import com.project.product_management.email.EmailService;
import com.project.product_management.email.EmailTemplate;
import com.project.product_management.exceptionhandling.valueNotFound;
import com.project.product_management.model.Orders;
import com.project.product_management.model.customer;
import com.project.product_management.model.product;
import com.project.product_management.pojo.*;
import com.project.product_management.repository.repositoryCustomer;
import com.project.product_management.repository.repositoryOrder;
import com.project.product_management.repository.repositoryProduct;
import com.project.product_management.service.OrderService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;
import com.project.product_management.controller.ApiControl;
import javax.jws.WebParam;
import java.io.File;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.project.product_management.service.impl.OrderServiceImpl;

@Controller
public class Htmlcontroller {

    @Autowired
    private repositoryCustomer RepositoryCustomer;

    @Autowired
    private repositoryOrder RepositoryOrder;

    @Autowired
    private repositoryProduct RepositoryProduct;

    @Autowired
    private OrderService orderService;

    @Autowired
    private EmailService emailService;



    @GetMapping(value = "/mainpage")
    public String getMainPage() {
        return "mainpage";
    }

    @GetMapping(value = "/Customer")
    public String getCustomerPage() {
        return "CustomerPage";
    }

    @GetMapping(value = "/Product")
    public String getProductPage() {
        return "ProductPage";
    }

    @GetMapping(value = "/Order")
    public String getorderPage() {
        return "OrderPage";
    }

    @GetMapping(value = "/Customer/display")
    public String getCustomerDisplay(Model model) throws Exception {
        List<customer> customers = RepositoryCustomer.getAllCustomer();
        model.addAttribute("customers", customers);
        return "DisplayCustomer";
    }

    @GetMapping(value = "/Product/display")
    public String getProductDisplay(Model model) throws Exception {
        List<product> products = RepositoryProduct.getAllProduct();
        for (int i=0;i<products.size();i++)
            System.out.println(products.get(i).getFilename());
        model.addAttribute("products", products);
        return "DisplayProduct";
    }

    @GetMapping(value = "/Order/display")
    public String getOrderDisplay(Model model) throws Exception {
        List<Map<String, Object>> orders = RepositoryOrder.getAllOrder();
        model.addAttribute("orders", orders);
        return "DisplayOrder";
    }

    @GetMapping(value = "/Order/Product/Sales/{id}")
    public String getProductSales(Model model,@PathVariable("id")Long id) throws Exception {
        List<ProductPrice> productPrices = orderService.getProductSale(id);
        model.addAttribute("purchases",productPrices);
        return "TotalSales";
    }

    @GetMapping(value = "/Order/Customer/Sales/{id}")
    public String getCustomerPurchases(Model model,@PathVariable("id")Long id) throws Exception{
        List<TotalPurchaseWithId> totalPurchaseWithIds = orderService.gettotalpurchase(id);
        model.addAttribute("payments",totalPurchaseWithIds);
        return "TotalPurchase";
    }


    @GetMapping(value = "/Customer/add/{id}")
    public String addCustomer(@PathVariable(value = "id")long id, Model model){
        customer customers = new customer();
        if(id != 0) {
            customers.setCustomer_id(id);
            customers.setCustomer_name(RepositoryCustomer.getNameById(id));
        }
        model.addAttribute("customers",customers);
        return "AddCustomer";

    }

    @GetMapping(value = "/Customer/delete/{id}")
    public String deleteCustomer(@PathVariable(value = "id")long id)
            throws Exception{
        List<Orders> orders = RepositoryOrder.getOrders(id);

        for(int i = 0;i<orders.size();i++){
            orders.get(i).setCustomerId(null);
            RepositoryOrder.save(orders.get(i));
        }
            customer customers = RepositoryCustomer.findById(id)
                    .orElseThrow(()-> new Exception("Customer Not found For this Id: "+id));

        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setSendto(RepositoryCustomer.getEmailById(id));
        emailTemplate.setBody("Account Has Been Deleted");
        emailTemplate.setSubject("DELETE");
        emailService.sendTextEmail(emailTemplate);
        RepositoryCustomer.delete(customers);
        return "redirect:/Customer/display";
    }

    @GetMapping(value = "/Product/add/{id}")
    public String addProduct(@PathVariable(value = "id")long id, Model model){
        product products = new product();
        if(id != 0) {
            products.setProduct_id(id);
            products.setProduct_price(RepositoryProduct.getPriceById(id));
            products.setProduct_name(RepositoryProduct.getNameById(id));
        }
        model.addAttribute("products",products);
        return "AddProduct";

    }

    @GetMapping(value = "/Product/delete/{id}")
    public String deleteProduct(@PathVariable(value = "id")long id)
            throws Exception{
        List<Orders> orders = RepositoryOrder.getOrder(id);

        for(int i = 0;i<orders.size();i++){
            orders.get(i).setProduct(null);
            RepositoryOrder.save(orders.get(i));
        }
        product products = RepositoryProduct.findById(id)
                .orElseThrow(()-> new Exception("Product Not found For this Id: "+id));
        RepositoryProduct.delete(products);
        String file = products.getFilename();
        String path = OrderServiceImpl.uploadDirectory + "/" + file;
        File deleteFile = new File(path);
        deleteFile.delete();
        return "redirect:/Product/display";
    }

    @GetMapping(value = "/Order/add/{id}")
    public String addOrder(@PathVariable(value = "id")Long id, Model model)throws Exception{
        OrderPojo orderPojo = new OrderPojo();
        if(id != 0 ) {
            orderPojo.setOrder_id(id);
        }
        model.addAttribute("orders",orderPojo);
        return "AddOrder";

    }

    @GetMapping(value = "/Order/delete/{id}")
    public String deleteOrder(@PathVariable(value = "id")long id)
            throws Exception{
        Orders orders = RepositoryOrder.getOrderById(id);
        orders.setCustomerId(null);
        orders.setProduct(null);
        RepositoryOrder.delete(orders);
        return "redirect:/Order/display";
    }

    @GetMapping(value = "/Order/delete/all")
    public String deleteOrderAll()
            throws Exception{
        List<Orders> orders = RepositoryOrder.getAllOrderDefault();
        for(int i = 0 ; i<orders.size();i++) {
            orders.get(i).setCustomerId(null);
            orders.get(i).setProduct(null);
            RepositoryOrder.delete(orders.get(i));
        }
        return "redirect:/Order/display";
    }

    @GetMapping(value="/Order/product/date")
    public String showDate(Model model, @ModelAttribute("dates")DatetimeSelect datetimeSelect) throws Exception {
        if(datetimeSelect.getFromDate()==null || datetimeSelect.getFromDate()==null) {
            long millis = System.currentTimeMillis();
            Date fromDate = new Date(millis);
            Date toDate = new Date(millis);
            datetimeSelect.setFromDate(fromDate);
            datetimeSelect.setToDate(toDate);
        }
        long totalSales = 0;
            long totalPurchase = 0;
            List<OutputDate> out = RepositoryOrder.listOfOrderBetweenDate(datetimeSelect.getFromDate(),datetimeSelect.getToDate());
            for(int i = 0 ; i<out.size();i++){
                totalPurchase+=out.get(i).getTotal_price();
                totalSales+=out.get(i).getQuantity();
            }
            OutputDate main = new OutputDate();
            main.setTotal_price(totalPurchase);
            main.setQuantity(totalSales);
        model.addAttribute("outputs",main);
        return "DateSelect";
    }

    @GetMapping(value = "/Email")
    public String showEmailPage(){
        return "Email";
    }

    @GetMapping(value = "/Email/TextMail")
    public String sendEmailWithoutFile(Model model){
        EmailTemplate emailTemplate = new EmailTemplate();
        model.addAttribute("mails",emailTemplate);
        return "EmailTextOnly";
    }

    @GetMapping(value = "/Email/AttachmentFile")
    public String sendEmailWithFile(Model model){
        EmailTemplate emailTemplate = new EmailTemplate();
        model.addAttribute("mails",emailTemplate);
        return "EmailAttachmentFile";
    }

    @GetMapping(value = "/Order/Customer/Email/{id}")
    public String sendCustomerMail(Model model,@PathVariable("id")Long id) throws Exception{
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setSendto(RepositoryCustomer.getEmailById(id));
        model.addAttribute("mails",emailTemplate);
        return "EmailTextOnly";
    }
/*
    @GetMapping(value = "/TaskPerformed")
    public String index(){
        return "index";
    }
    */
}
