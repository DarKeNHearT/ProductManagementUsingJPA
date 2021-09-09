package com.project.product_management.repository;

import com.project.product_management.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface RepositoryUser extends JpaRepository<User,Long> {
    Optional<User> findByUserName(String userName);


}
