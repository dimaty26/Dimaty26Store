package com.zmeev.oauth2Demo.services;

import com.zmeev.oauth2Demo.entities.OrderProduct;
import com.zmeev.oauth2Demo.repos.OrderProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderProductService {

    private OrderProductRepository orderProductRepository;

    public OrderProductService(OrderProductRepository orderProductRepository) {
        this.orderProductRepository = orderProductRepository;
    }

    public OrderProduct create(OrderProduct orderProduct) {
        return orderProductRepository.save(orderProduct);
    }
}
