package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderFarmerService {

    private final OrderFarmerRepository orderFarmerRepository;
    private final FarmerRepository farmerRepository;
    private final AuthRepository authRepository;
    private final CompanyRepository companyRepository;
    private final CustomerRepository customerRepository;

    //ADMIN
    public List<OrderFarmer> getAllOrderFarmer(){
        if (orderFarmerRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return orderFarmerRepository.findAll();
    }

    //COMPANY - CUSTOMER
    //khaled
    public void requestFarmer(Integer userId, Integer farmerId, OrderFarmer orderFarmer){

        Farmer farmer = farmerRepository.findFarmerById(farmerId);
        OrderFarmer orderFarmer1 = new OrderFarmer();
        orderFarmer1.setTotalPrice(farmer.getPrice()*orderFarmer.getDuration());
        orderFarmer1.setDateAndTime(orderFarmer.getDateAndTime());
        orderFarmer1.setComment(null);
        orderFarmer1.setDuration(orderFarmer.getDuration());
        orderFarmer1.setStatus("waiting");
        orderFarmer1.setFarmer(farmer);
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orderFarmer1.setCustomer(customer);
            orderFarmerRepository.save(orderFarmer1);

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orderFarmer1.setCompany(company);
            orderFarmerRepository.save(orderFarmer1);

        }
    }

    //khaled
    //company - customer
    public void cancelOrderFarmer(Integer userId, Integer orderFarmerId){
        User user = authRepository.findUserById(userId);
        List<OrderFarmer> orderFarmers = new ArrayList<>();
        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orderFarmers = orderFarmerRepository.findOrderFarmerByCustomerId(customer.getId());

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orderFarmers = orderFarmerRepository.findOrderFarmerByCompanyId(company.getId());
        }

        for (OrderFarmer orderFarmer : orderFarmers) {
            if (orderFarmer.getId().equals(orderFarmerId)){
                if(orderFarmer.getStatus().equalsIgnoreCase("waiting")){
                    orderFarmer.setStatus("Canceled");}}
        }
    }

    //-------------------------------------   end CRUD  ---------------------------

    //sara
    public List<OrderFarmer> NewOrdersToFarmer(Integer farmerId) {
        ArrayList<OrderFarmer> orders2 = new ArrayList<>();
        List<OrderFarmer> orders3 = orderFarmerRepository.findOrderFarmerByFarmerId(farmerId);

        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderFarmer orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("Waiting")){
                orders2.add(orders1);
            }}

        return orders2;
    }
    //sara
    public List<OrderFarmer> currentOrdersToFarmer(Integer userId) {
        ArrayList<OrderFarmer> orders2 = new ArrayList<>();
        List<OrderFarmer> orders3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orders3=orderFarmerRepository.findOrderFarmerByCustomerId(customer.getId());


        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orders3 = orderFarmerRepository.findOrderFarmerByCompanyId(company.getId());

        }
        else if (user.getRole().equalsIgnoreCase("FARMER")) {
            Farmer farmer = farmerRepository.findFarmerById(userId);
            orders3 = orderFarmerRepository.findOrderFarmerByFarmerId(farmer.getId());

        }

        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderFarmer orders1 : orders3) {
            if ( orders1.getStatus().equalsIgnoreCase("accepted") ||orders1.getStatus().equalsIgnoreCase("Waiting")  ){
                orders2.add(orders1);
            }}

        return orders2;
    }
    //sara
    public List<OrderFarmer> previousOrdersToFarmer(Integer userId) {
        ArrayList<OrderFarmer> orders2 = new ArrayList<>();
        List<OrderFarmer> orders3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orders3=orderFarmerRepository.findOrderFarmerByCustomerId(customer.getId());

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orders3 = orderFarmerRepository.findOrderFarmerByCompanyId(company.getId());
        }
        else if (user.getRole().equalsIgnoreCase("FARMER")) {
            Farmer farmer = farmerRepository.findFarmerById(userId);
            orders3 = orderFarmerRepository.findOrderFarmerByFarmerId(farmer.getId());
        }
        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderFarmer orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("finished")|| orders1.getStatus().equalsIgnoreCase("Rejected")||orders1.getStatus().equalsIgnoreCase("Canceled")){
                orders2.add(orders1);
            }}

        return orders2;
    }

    public void updateStatusOrderFarmer (Integer farmerId, Integer orderId) {
        OrderFarmer orderFarmer = orderFarmerRepository.findOrderFarmerById(orderId);
        //   @Pattern(regexp ="^(Planting done|Rejected|waiting|accepted)$")

        if (orderFarmer == null) {
            throw new ApiException("orderFarmer id not found");
        }
        if (orderFarmer.getFarmer().getId()!= farmerId) {
            throw new ApiException("orderFarmer not for you");
        }
        //@Pattern(regexp ="^(Delivered|Ready to deliver|waiting|accepted|Rejected)$")

        if (orderFarmer.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("orderFarmer is rejected can not changed");
        }
        if (orderFarmer.getStatus().equalsIgnoreCase("Planting done")) {
            throw new ApiException("orderFarmer is Planting done already");
        }

        else if (orderFarmer.getStatus().equalsIgnoreCase("waiting")) {
            orderFarmer.setStatus("accepted");
        } else if (orderFarmer.getStatus().equalsIgnoreCase("accepted")) {
            orderFarmer.setStatus("Planting done");
        }
        orderFarmerRepository.save(orderFarmer);
    }

    public void acceptOrderFarmer(Integer farmerId, Integer orderId) {
        OrderFarmer orderFarmer = orderFarmerRepository.findOrderFarmerById(orderId);

        if (orderFarmer == null) {
            throw new ApiException("orderFarmer id not found");
        }

        if (orderFarmer.getFarmer().getId()!= farmerId) {
            throw new ApiException("orderFarmer not for you");
        }

        orderFarmer.setStatus("accepted");
        orderFarmerRepository.save(orderFarmer);

    }

    ///3
    public void rejectOrderFarmer(Integer farmerId, Integer orderId) {
        OrderFarmer orderFarmer = orderFarmerRepository.findOrderFarmerById(orderId);

        if (orderFarmer == null) {
            throw new ApiException("orderFarmer id not found");
        }

        if (orderFarmer.getFarmer().getId()!= farmerId) {
            throw new ApiException("orderFarmer not for you");
        }

        orderFarmer.setStatus("Rejected");
        orderFarmerRepository.save(orderFarmer);

    }


    public OrderFarmer retrieveById(Integer orderId){
        OrderFarmer orderFarmer = orderFarmerRepository.findOrderFarmerById(orderId);

        if (orderFarmer == null) {
            throw new ApiException("orderFarmer id not found");
        }

        return orderFarmer;
    }



    // يعرض قائمة على حسب حالة الطلب للمزارع
    public List<OrderFarmer> retrieveAllbyStatus(String status){

        List<OrderFarmer> orderFarmer = orderFarmerRepository.findAllByStatus(status);

        if (orderFarmer .isEmpty()) {
            throw new ApiException("orderFarmer list with status "+status+" empty");
        }
        return orderFarmer ;

    }

    //khaled
    //customer company
    public void commentOnOrderFarmer(Integer userId, Integer orderFarmerId, String comment){
        OrderFarmer orderFarmer = orderFarmerRepository.findOrderFarmerById(orderFarmerId);
        if(orderFarmer == null){
            throw new ApiException("order farmer not found");
        }
        if(orderFarmer.getCustomer().getId()!=userId && orderFarmer.getCompany().getId()!=userId){
            throw new ApiException("invalid comment request");
        }
        if(!orderFarmer.getStatus().equalsIgnoreCase("finished")){
            throw new ApiException("once the order is finished you can comment");
        }

        orderFarmer.setComment(comment);
        orderFarmerRepository.save(orderFarmer);
    }
//sara
    public void evaluationOnOrderFarmer(Integer userId, Integer orderId, Double evaluation){
        Double evaluate=0.0;
        OrderFarmer orderFarmer = orderFarmerRepository.findOrderFarmerById(orderId);
        if(orderFarmer == null){
            throw new ApiException("order plant not found");
        }
        if(!orderFarmer.getStatus().equalsIgnoreCase("finished")){
            throw new ApiException("invalid evaluation request");
        }
        if(orderFarmer.getCustomer().getId()==userId || orderFarmer.getCompany().getId()==userId){
            orderFarmer.setEvaluation(evaluation);
            orderFarmerRepository.save(orderFarmer);
        }


        List<OrderFarmer> orderFarmers = orderFarmerRepository.findOrderFarmerByFarmerId(orderFarmer.getFarmer().getId());
        for (OrderFarmer orderFarmer1 : orderFarmers) {
            if(orderFarmer1.getStatus().equalsIgnoreCase("finished") && orderFarmer1.getEvaluation()!= null){
                evaluate+=orderFarmer1.getEvaluation();
            }
        }

        orderFarmer.getFarmer().setNumberOfEvaluation(orderFarmer.getFarmer().getNumberOfEvaluation()+1);
        orderFarmer.getFarmer().setEvaluation((evaluate/orderFarmer.getFarmer().getNumberOfEvaluation()));
      orderFarmerRepository.save(orderFarmer);
       farmerRepository.save(orderFarmer.getFarmer());

    }

}
