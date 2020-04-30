package com.zmeev.oauth2Demo.repos;

import com.zmeev.oauth2Demo.entities.CartItem;
import com.zmeev.oauth2Demo.entities.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByShoppingCart(ShoppingCart shoppingCart);
}
