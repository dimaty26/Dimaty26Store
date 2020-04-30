package com.zmeev.oauth2Demo.services;

import com.zmeev.oauth2Demo.entities.CartItem;
import com.zmeev.oauth2Demo.entities.ShoppingCart;
import com.zmeev.oauth2Demo.repos.ShoppingCartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ShoppingCartService {

    private ShoppingCartRepository shoppingCartRepository;
    private CartItemService cartItemService;

    @Autowired
    public ShoppingCartService(ShoppingCartRepository shoppingCartRepository,
                                CartItemService cartItemService) {
        this.shoppingCartRepository = shoppingCartRepository;
        this.cartItemService = cartItemService;
    }

    public ShoppingCart updateShoppingCart(ShoppingCart shoppingCart) {

        BigDecimal cartTotal = new BigDecimal(0);

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        for (CartItem cartItem : cartItemList) {
            if (cartItem.getProduct().getQuantity() > 0) {
                cartItemService.updateCartItem(cartItem);
                cartTotal = cartTotal.add(cartItem.getSubtotal());
            }
        }

        shoppingCart.setGrandTotal(cartTotal);

        shoppingCartRepository.save(shoppingCart);

        return shoppingCart;
    }
}
