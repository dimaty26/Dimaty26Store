package com.zmeev.oauth2Demo.entities;

import javax.persistence.*;

@Entity
public class ProductToCartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;
}
