package com.zmeev.oauth2Demo.entities;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "category")
@Data
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "category_name")
    private String categoryName;

    @Column(nullable = false)
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> productSet;

    public Set<Product> getProduct() {
        return productSet;
    }

    public void setProduct(Product product) {
        if (productSet.size() == 0) {
            productSet = new HashSet<>();
        }
        productSet.add(product);
    }
}
