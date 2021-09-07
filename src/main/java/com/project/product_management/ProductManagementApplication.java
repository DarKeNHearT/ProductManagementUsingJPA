package com.project.product_management;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.project.product_management.service.impl.OrderServiceImpl;

import java.io.File;

@SpringBootApplication
public class ProductManagementApplication {

	public static void main(String[] args) {
		new File(OrderServiceImpl.uploadDirectory).mkdir();
		SpringApplication.run(ProductManagementApplication.class, args);
	}

}
