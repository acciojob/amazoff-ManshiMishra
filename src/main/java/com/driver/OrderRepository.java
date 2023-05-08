package com.driver;

import java.util.*;

public class OrderRepository {

    HashMap<String,Order>orderDb=new HashMap<>();
    HashMap<String,DeliveryPartner>partnerDb=new HashMap<>();
    HashMap<String, List<String>>orderPartnerDb=new HashMap<>();
    HashSet<String>unassignDb=new HashSet<>();

    public void addOrder(Order order){
      orderDb.put(order.getId(),order);
      unassignDb.add(order.getId());
    }
    public void addPartner(String partnerId) {
        DeliveryPartner pId=new DeliveryPartner(partnerId);
        partnerDb.put(partnerId,pId);
    }

    public void addOrderPartnerPair(String orderId, String partnerId) {
        List<String> list=orderPartnerDb.getOrDefault(partnerId,new ArrayList<>());
        list.add(orderId);
        orderPartnerDb.put(partnerId,list);
        partnerDb.get(partnerId).setNumberOfOrders(partnerDb.get(partnerId).getNumberOfOrders()+1);

        unassignDb.remove(orderId);
    }
    public Order getOrderById(String orderId) {
//        for(Map.Entry<String,Order>find:orderDb.entrySet()){
//            if(find.getKey()==orderId){
//                return find.getValue();
//            }
//        }
//        return null;
        return orderDb.get(orderId);
    }
    public DeliveryPartner getPartnerById(String partnerId) {
        return partnerDb.get(partnerId);
    }
    public Integer getOrderCountByPartnerId(String partnerId) {
        return orderPartnerDb.size();
    }

    public List<String> getOrdersByPartnerId(String partnerId) {
        List<String>ans=orderPartnerDb.getOrDefault(partnerId,new ArrayList<>());
        return ans;
    }

    public List<String> getAllOrders() {
        ArrayList<String>ans=new ArrayList<>();
        for(String s: orderDb.keySet()){
            ans.add(s);
        }
        return ans;
    }
    public Integer getCountOfUnassignedOrders() {
        return unassignDb.size();
    }
    public Integer getOrdersLeftAfterGivenTimeByPartnerId(String time, String partnerId) {
        Integer count=0;
        //converting given string time to integer
        String arr[]=time.split(":"); //12:45
        int hr=Integer.parseInt(arr[0]);
        int min=Integer.parseInt(arr[1]);

        int total=(hr*60+min);

        List<String> list=orderPartnerDb.getOrDefault(partnerId,new ArrayList<>());
        if(list.size()==0)return 0; //no order assigned to partnerId

        for(String s: list){
            Order currentOrder=orderDb.get(s);
            if(currentOrder.getDeliveryTime()>total){
                count++;
            }
        }

        return count;
    }
    public String getLastDeliveryTimeByPartnerId(String partnerId) {
        String str="00:00";
        int max=0;

        List<String>list=orderPartnerDb.getOrDefault(partnerId,new ArrayList<>());
        if(list.size()==0)return str;
        for(String s: list){
            Order currentOrder=orderDb.get(s);
            max=Math.max(max,currentOrder.getDeliveryTime());
        }
        //convert int to string (140-> 02:20)
        int hr=max/60;
        int min=max%60;

        if(hr<10){
            str="0"+hr+":";
        }else{
            str=hr+":";
        }

        if(min<10){
            str+="0"+min;
        }
        else{
            str+=min;
        }
        return str;
    }
    public void deletePartnerById(String partnerId) {
        if(!orderPartnerDb.isEmpty()){
            unassignDb.addAll(orderPartnerDb.get(partnerId));
        }

        partnerDb.remove(partnerId);

        orderPartnerDb.remove(partnerId);
    }
    public void deleteOrderById(String orderId) {
        if(orderDb.containsKey(orderId)){
            if(unassignDb.contains(orderId)){
                unassignDb.remove(orderId);
            }
            else{

                for(String str : orderPartnerDb.keySet()){
                    List<String> list=orderPartnerDb.get(str);
                    list.remove(orderId);
                }
            }
        }
    }
}
