package com.zmeev.oauth2Demo.repos;

import com.zmeev.oauth2Demo.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
}
