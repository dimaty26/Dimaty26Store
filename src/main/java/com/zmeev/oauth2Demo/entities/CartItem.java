package com.zmeev.oauth2Demo.entities;

import lombok.Data;
import org.codehaus.jackson.annotate.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int quantity;
    private BigDecimal subtotal;

    @OneToOne
    private Product product;

    @OneToMany(mappedBy = "cartItem")
    @JsonIgnore
    private List<ProductToCartItem> bookToCartItemList;

    @ManyToOne
    @JoinColumn(name = "shoppingCart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
