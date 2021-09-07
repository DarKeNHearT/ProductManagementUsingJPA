package com.project.product_management.service.impl;

import com.project.product_management.model.Orders;
import com.project.product_management.model.product;
import com.project.product_management.pojo.*;
import com.project.product_management.repository.repositoryOrder;
import com.project.product_management.repository.repositoryProduct;
import com.project.product_management.service.OrderService;
import javassist.NotFoundException;
import netscape.javascript.JSUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.project.product_management.repository.repositoryCustomer;
import com.project.product_management.model.customer;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private repositoryOrder repositoryOrder;

    @Autowired
    private repositoryProduct repositoryProduct;

    @Autowired
    private repositoryCustomer repositoryCustomers;

    public static String uploadDirectory =System.getProperty("user.dir") + "/uploads/";

    @Override
    @Transactional
    public OrderPojo saveit(OrderPojo orderPojo) throws Exception {
        Orders o;
        if (orderPojo.getOrder_id() != null) {
            o = checkit(orderPojo.getOrder_id());
        } else {
            o = new Orders();
        }
        o.setQuantity(orderPojo.getQuantity());
        if(repositoryProduct.getUserIdByName(orderPojo.getProduct()) ==null || repositoryCustomers.getUserIdByName(orderPojo.getCustomer())==null)
            throw new Exception("No Such Product Or Customer Was Found");

        Optional<product> pr = repositoryProduct.findById(repositoryProduct.getUserIdByName(orderPojo.getProduct()));
        if (!pr.isPresent()) {
            throw new Exception("product not present");
        }

        Optional<customer> hr = repositoryCustomers.findById(repositoryCustomers.getUserIdByName(orderPojo.getCustomer()));
        if(!hr.isPresent()) {
            throw new Exception("Customer Not Found");
        }
        List<ListOfProduct> check = repositoryOrder.listOfOrderProduct(repositoryCustomers.getUserIdByName(orderPojo.getCustomer()));
        for(int i =0;i<check.size();i++){
            if(check.get(i).getProduct_id() == repositoryProduct.getUserIdByName(orderPojo.getProduct()))
                throw new Exception("Product already Present For Given Customer!!!");

        }
        o.setProduct(pr.get());
        o.setCustomerId(hr.get());

        Long totalPrice = o.getQuantity()*repositoryProduct.getPriceById(repositoryProduct.getUserIdByName(orderPojo.getProduct()));
        o.setTotal_price(totalPrice);

        Orders o1 = repositoryOrder.save(o);

        orderPojo.setOrder_id(o1.getOrder_id());

        return orderPojo;
    }

    @Override
    public Orders checkit(Long id) throws NotFoundException {
        Optional<Orders> o = repositoryOrder.findById(id);
        if (o.isPresent()) {
            return o.get();
        }
        throw new NotFoundException("not found");
    }

    @Override
    public ResponseEntity<ProductPrice> getproductsale(long productId) throws Exception {
        Optional<product> pr = repositoryProduct.findById(productId);
        long totalSales= 0;
        long totalPrice =0;
        if(!pr.isPresent()){
            throw new Exception("Product not Found");
        }

        List<Totalprice> totalprices = repositoryOrder.getTotalSales(productId);
        for(int i=0;i<totalprices.size();i++){
            totalSales +=totalprices.get(i).getQuantity();
        }
        for(int i=0;i<totalprices.size();i++){
            totalPrice +=totalprices.get(i).getTotal_price();
        }
        ProductPrice sale = new ProductPrice();
        sale.setTotalPrice(totalPrice);
        sale.setProductId(productId);
        sale.setName(repositoryProduct.getNameById(productId));
        sale.setTotalSales(totalSales);
        return ResponseEntity.ok().body(sale);
    }

    @Override
    public ResponseEntity<TotalPurchaseWithId> getTotalPurchase(long id) throws Exception{
        Optional<customer> cu = repositoryCustomers.findById(id);
        List<String> products= new ArrayList<String>();
        if(!cu.isPresent())
            throw new Exception("Customer Not Found");
        List<TotalPurchase> lists = repositoryOrder.getTotalPurchases(id);
        long sum = 0;
        for(int i = 0 ; i<lists.size();i++) {
            sum+=lists.get(i).getTotal_purchase();
            products.add(lists.get(i).getName());
        }

        TotalPurchaseWithId main = new TotalPurchaseWithId();
        main.setTotal_purchase(sum);
        main.setCustomerName(repositoryCustomers.getNameById(id));
        main.setCustomer_id(id);
        main.setName(products);
        return ResponseEntity.ok().body(main);
    }

    @Override
    public ResponseEntity<OutputDate> SelectDate(DatetimeSelect datetimeSelect) throws Exception{
        String formatDate = "YYYY-MM-DD";
        long totalSales = 0;
        long totalPurchase = 0;
        DateFormat df = new SimpleDateFormat(formatDate);
        String fromDate = df.format(datetimeSelect.getFromDate());
        String toDate = df.format(datetimeSelect.getToDate());
        List<OutputDate> out = repositoryOrder.listOfOrderBetweenDate(datetimeSelect.getFromDate(),datetimeSelect.getToDate());
        if(out.isEmpty())
            throw new Exception("No Data Found For Following Date");
        for(int i = 0 ; i<out.size();i++){
            totalPurchase+=out.get(i).getTotal_price();
            totalSales+=out.get(i).getQuantity();
        }
        OutputDate main = new OutputDate();
        main.setTotal_price(totalPurchase);
        main.setQuantity(totalSales);
        return ResponseEntity.ok().body(main);

    }

    @Override
    public List<customer> getAllCustomer(){
        return repositoryCustomers.findAll();
    }

    @Override
    public List<product> getAllProduct(){
        return repositoryProduct.findAll();
    }

    @Override
    public List<ProductPrice> getProductSale(long productId) throws Exception {
        Optional<product> pr = repositoryProduct.findById(productId);
        long totalSales= 0;
        long totalPrice =0;
        if(!pr.isPresent()){
            throw new Exception("Product not Found");
        }
        List<Totalprice> totalprices = repositoryOrder.getTotalSales(productId);
        for(int i=0;i<totalprices.size();i++){
            totalSales +=totalprices.get(i).getQuantity();
        }
        for(int i=0;i<totalprices.size();i++){
            totalPrice +=totalprices.get(i).getTotal_price();
        }
        ProductPrice sale = new ProductPrice();
        sale.setTotalPrice(totalPrice);
        sale.setProductId(productId);
        sale.setName(repositoryProduct.getNameById(productId));
        sale.setTotalSales(totalSales);
        List<ProductPrice> sales = new ArrayList<>();
        sales.add(sale);
        return sales;
    }

    @Override
    public List<TotalPurchaseWithId> gettotalpurchase(long id) throws Exception{
        Optional<customer> cu = repositoryCustomers.findById(id);
        List<String> products= new ArrayList<String>();
        if(!cu.isPresent())
            throw new Exception("Customer Not Found");
        List<TotalPurchase> lists = repositoryOrder.getTotalPurchases(id);
        long sum = 0;
        for(int i = 0 ; i<lists.size();i++) {
            sum+=lists.get(i).getTotal_purchase();
            products.add(lists.get(i).getName());
        }

        TotalPurchaseWithId main = new TotalPurchaseWithId();
        main.setTotal_purchase(sum);
        main.setCustomerName(repositoryCustomers.getNameById(id));
        main.setCustomer_id(id);
        main.setName(products);
        List<TotalPurchaseWithId> list_main = new ArrayList<>();
        list_main.add(main);
        return list_main;
    }
    @Override
    public List<OutputDate> selectdate(DatetimeSelect datetimeSelect) throws Exception{
        String formatDate = "YYYY-MM-DD";
        long totalSales = 0;
        long totalPurchase = 0;
        DateFormat df = new SimpleDateFormat(formatDate);
        String fromDate = df.format(datetimeSelect.getFromDate());
        String toDate = df.format(datetimeSelect.getToDate());
        List<OutputDate> out = repositoryOrder.listOfOrderBetweenDate(datetimeSelect.getFromDate(),datetimeSelect.getToDate());
        if(out.isEmpty())
            throw new Exception("No Data Found For Following Date");
        for(int i = 0 ; i<out.size();i++){
            totalPurchase+=out.get(i).getTotal_price();
            totalSales+=out.get(i).getQuantity();
        }
        OutputDate main = new OutputDate();
        main.setTotal_price(totalPurchase);
        main.setQuantity(totalSales);
        List<OutputDate> list_main= new ArrayList<>();
        list_main.add(main);
        return list_main;

    }
    public void SaveProduct(MultipartFile file,String name,Long id,long price)throws  Exception{
        product products = new product();

        StringBuilder filepath = new StringBuilder();

        String fileName = file.getOriginalFilename();
        String filePath = Paths.get(uploadDirectory,fileName).toString();
        if(fileName.contains("..")){
            throw new Exception("Not a Valid File");
        }

        //locally saving file
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
        stream.write(file.getBytes());
        stream.close();

        products.setImage(filePath);
        products.setProduct_name(name);
        products.setProduct_price(price);
        products.setProduct_id(id);
        products.setFilename(fileName);
        repositoryProduct.save(products);
    }

}
