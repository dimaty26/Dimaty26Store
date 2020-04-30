package com.zmeev.oauth2Demo.services;

import com.zmeev.oauth2Demo.entities.Order;
import com.zmeev.oauth2Demo.repos.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Order create(Order order) {
        order.setDateCreated(LocalDate.now());
        return orderRepository.save(order);
    }

    public void update(Order order) {
        orderRepository.save(order);
    }
}
