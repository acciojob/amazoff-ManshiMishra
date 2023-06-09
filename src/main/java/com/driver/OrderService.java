package com.driver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public class OrderService {
    @Autowired
    OrderRepository orderRepository;
    public void addOrder(Order order){
        orderRepository.addOrder(order);
    }
    public void addPartner(String partnerId){
        orderRepository.addPartner(partnerId);
    }
    public void addOrderPartnerPair(String orderId, String partnerId){
        orderRepository.addOrderPartnerPair(orderId, partnerId);
    }
    public Order getOrderById(String orderId){
        return orderRepository.getOrderById(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId){
        return orderRepository.getPartnerById(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId){
        return orderRepository.getOrderCountByPartnerId(partnerId);
    }
    public List<String> getOrdersByPartnerId(String partnerId){
        return orderRepository.getOrdersByPartnerId(partnerId);
    }
    public List<String> getAllOrders(){
        return orderRepository.getAllOrders();
    }
    public Integer getCountOfUnassignedOrders(){
        return orderRepository.getCountOfUnassignedOrders();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time,String partnerId){
        return orderRepository.getOrdersLeftAfterGivenTimeByPartnerId(time,partnerId);
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId){
         String ans=orderRepository.getLastDeliveryTimeByPartnerId(partnerId);
         return ans;
    }
    public void deletePartnerById(String partnerId){
        orderRepository.deletePartnerById(partnerId);
    }
    public void deleteOrderById( String orderId){
        orderRepository.deleteOrderById(orderId);
    }
}
