package com.zmeev.oauth2Demo.services;

import com.zmeev.oauth2Demo.entities.CartItem;
import com.zmeev.oauth2Demo.entities.ShoppingCart;
import com.zmeev.oauth2Demo.repos.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class CartItemService {

    private CartItemRepository cartItemRepository;

    @Autowired
    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public List<CartItem> findByShoppingCart(ShoppingCart shoppingCart) {
        return cartItemRepository.findByShoppingCart(shoppingCart);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        BigDecimal bigDecimal = cartItem.getProduct().getPrice().multiply(new BigDecimal(cartItem.getQuantity()));

        bigDecimal = bigDecimal.setScale(2, RoundingMode.HALF_UP);
        cartItem.setSubtotal(bigDecimal);

        cartItemRepository.save(cartItem);

        return cartItem;
    }
}
