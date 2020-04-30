package com.zmeev.oauth2Demo.repos;

import com.zmeev.oauth2Demo.entities.OrderProduct;
import com.zmeev.oauth2Demo.entities.OrderProductPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderProductRepository extends JpaRepository<OrderProduct, OrderProductPK> {
}
