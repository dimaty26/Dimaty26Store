package com.zmeev.oauth2Demo.controllers;

import com.zmeev.oauth2Demo.entities.CartItem;
import com.zmeev.oauth2Demo.entities.ShoppingCart;
import com.zmeev.oauth2Demo.entities.User;
import com.zmeev.oauth2Demo.services.CartItemService;
import com.zmeev.oauth2Demo.services.ShoppingCartService;
import com.zmeev.oauth2Demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/shoppingCart")
public class ShoppingCartController {

    private UserService userService;
    private CartItemService cartItemService;
    private ShoppingCartService shoppingCartService;

    @Autowired
    public ShoppingCartController(UserService userService,
                                  CartItemService cartItemService,
                                  ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.cartItemService = cartItemService;
        this.shoppingCartService = shoppingCartService;
    }

    @RequestMapping()
    public String shoppingCart(Model model, Principal principal) {
        User user = userService.loadUserByUsername(principal.getName());
        ShoppingCart shoppingCart = user.getShoppingCart();

        List<CartItem> cartItemList = cartItemService.findByShoppingCart(shoppingCart);

        shoppingCartService.updateShoppingCart(shoppingCart);

        model.addAttribute("cartItemList", cartItemList);
        model.addAttribute("shoppingCart", shoppingCart);

        return "shopping-cart-additional";
    }
}
