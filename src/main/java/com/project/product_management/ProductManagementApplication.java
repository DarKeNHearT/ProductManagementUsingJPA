package com.project.product_management;


import com.project.product_management.repository.RepositoryUser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.project.product_management.service.impl.OrderServiceImpl;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.io.File;

@SpringBootApplication
@EnableJpaRepositories(basePackageClasses = RepositoryUser.class)
public class ProductManagementApplication {

	public static void main(String[] args) {
		new File(OrderServiceImpl.uploadDirectory).mkdir();
		SpringApplication.run(ProductManagementApplication.class, args);
	}

}
