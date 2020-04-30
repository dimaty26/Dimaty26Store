package com.zmeev.oauth2Demo.repos;

import com.zmeev.oauth2Demo.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
