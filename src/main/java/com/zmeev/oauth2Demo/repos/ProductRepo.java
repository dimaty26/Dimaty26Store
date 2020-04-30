package com.zmeev.oauth2Demo.repos;

import com.zmeev.oauth2Demo.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepo extends JpaRepository<Product, Long> {
}
