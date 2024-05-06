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
public class OrderGuidanceService {
    private final CompanyRepository companyRepository;
    private final OrderGuidanceRepository orderGuidanceRepository;
    private final SpecialistRepository specialistRepository;
    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    //ADMIN
    public List<OrderGuidance> getAllOrderGuidance(){
        if (orderGuidanceRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return orderGuidanceRepository.findAll();
    }

    public void requestGuidance(Integer companyId, Integer specialistId, OrderGuidance orderGuidance){
        Company company = companyRepository.findCompanyById(companyId);
        Specialist specialist = specialistRepository.findSpecialistById(specialistId);
        if(specialist == null){
            throw new ApiException("specialist not found");
        }

        int price;

        if(orderGuidance.getType().equalsIgnoreCase("Presence")){
            price = specialist.getPricePresence()*orderGuidance.getDuration();
        }else if(orderGuidance.getType().equalsIgnoreCase("online")){
            price = specialist.getPriceOnline()*orderGuidance.getDuration();
        }else throw new ApiException("invalid type");

        orderGuidance.setTotalPrice(price);
        orderGuidance.setStatus("waiting");
        orderGuidance.setCompany(company);
        orderGuidance.setSpecialist(specialist);


        company.getOrderGuidances().add(orderGuidance);
        specialist.getOrderGuidances().add(orderGuidance);
        companyRepository.save(company);
        specialistRepository.save(specialist);
        orderGuidanceRepository.save(orderGuidance);
    }

    //khaled
    //company
    public void cancelOrderGuidance(Integer userId, Integer orderGuidanceId) {
        User user = authRepository.findUserById(userId);
        List<OrderGuidance> orderGuidances = new ArrayList<>();
        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orderGuidances = orderGuidanceRepository.findOrderGuidanceByCustomerId(customer.getId());

        } else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orderGuidances = orderGuidanceRepository.findOrderGuidanceByCompanyId(company.getId());
        }

        for (OrderGuidance orderGuidance : orderGuidances) {
            if (orderGuidance.getId().equals(orderGuidanceId)) {
                if (orderGuidance.getStatus().equalsIgnoreCase("waiting")) {
                    orderGuidance.setStatus("Canceled");
                }
            }
        }
    }
    //-------------------------------------   end CRUD  ---------------------------
//sara
    public List<OrderGuidance> NewOrdersToSpecialist(Integer specialistId) {
        ArrayList<OrderGuidance> orders2 = new ArrayList<>();
        List<OrderGuidance> orders3 = orderGuidanceRepository.findOrderGuidanceBySpecialistId(specialistId);

        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderGuidance orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("Waiting")){
                orders2.add(orders1);
            }}

        return orders2;
    }
//sara
    public List<OrderGuidance> currentOrdersToSpecialist(Integer userId) {
        ArrayList<OrderGuidance> orders2 = new ArrayList<>();
        List<OrderGuidance> orders3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orders3=orderGuidanceRepository.findOrderGuidanceByCustomerId(customer.getId());

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orders3 = orderGuidanceRepository.findOrderGuidanceByCompanyId(company.getId());

        }
        else if (user.getRole().equalsIgnoreCase("SPECIALIST")) {
            Specialist specialist = specialistRepository.findSpecialistById(userId);
            orders3 = orderGuidanceRepository.findOrderGuidanceBySpecialistId(specialist.getId());

        }

        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderGuidance orders1 : orders3) {
            if ( orders1.getStatus().equalsIgnoreCase("accepted")|| orders1.getStatus().equalsIgnoreCase("waiting")   ){
                orders2.add(orders1);
            }}

        return orders2;
    }
//sara
    public List<OrderGuidance> previousOrdersToSpecialist(Integer userId) {
        ArrayList<OrderGuidance> orders2 = new ArrayList<>();
        List<OrderGuidance> orders3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orders3=orderGuidanceRepository.findOrderGuidanceByCustomerId(customer.getId());

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orders3 = orderGuidanceRepository.findOrderGuidanceByCompanyId(company.getId());
        }
        else if (user.getRole().equalsIgnoreCase("SPECIALIST")) {
            Specialist specialist = specialistRepository.findSpecialistById(userId);
            orders3 = orderGuidanceRepository.findOrderGuidanceBySpecialistId(specialist.getId());
        }
        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderGuidance orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("finished")|| orders1.getStatus().equalsIgnoreCase("Rejected")||orders1.getStatus().equalsIgnoreCase("Canceled")){
                orders2.add(orders1);
            }}

        return orders2;
    }
    //khloud
    public void updateStatusOrderGuidance(Integer specialistId , Integer orderGuidanceId) {
        OrderGuidance orderGuidance =orderGuidanceRepository.findOrderGuidanceById(orderGuidanceId);

        if (orderGuidance.getSpecialist().getId()!=specialistId) {
            throw new ApiException("orderGuidance not for you");}

        if(orderGuidance==null) {
            throw new ApiException("orderGuidance id not found");
        }

        if (orderGuidance.getStatus().equalsIgnoreCase("Guided")) {
            throw new ApiException("orderGuidance is already Guided");
        }
        if (orderGuidance.getStatus().equalsIgnoreCase("Rejected")) {
            throw new ApiException("orderGuidance is rejected can not changed");
        }

        else if (orderGuidance.getStatus().equalsIgnoreCase("waiting")) {
            orderGuidance.setStatus("accepted");
        } else if (orderGuidance.getStatus().equalsIgnoreCase("accepted")) {
            orderGuidance.setStatus("Guided");
        }
        orderGuidanceRepository.save(orderGuidance);
    }

    //khloud
    public void accepctOrderGuidance(Integer specialistId , Integer orderId) {
        OrderGuidance  orderGuidance = orderGuidanceRepository.findOrderGuidanceById(orderId);

        if (orderGuidance==null) {
            throw new ApiException("orderGuidance id not found");
        }

        if (orderGuidance.getSpecialist().getId()!=specialistId) {
            throw new ApiException("orderGuidance not for you");}


        orderGuidance.setStatus("accepted");
        orderGuidanceRepository.save(orderGuidance);

    }

    //khloud
    public void rejectOrderGuidance(Integer specialistId , Integer orderId) {
        OrderGuidance  orderGuidance = orderGuidanceRepository.findOrderGuidanceById(orderId);

        if (orderGuidance==null) {
            throw new ApiException("orderGuidance id not found");
        }

        if (orderGuidance.getSpecialist().getId()!=specialistId) {
            throw new ApiException("orderGuidance not for you");}


        orderGuidance.setStatus("Rejected");
        orderGuidanceRepository.save(orderGuidance);

    }

    //khloud
    public OrderGuidance retrieveById(Integer orderId){
        OrderGuidance  orderGuidance = orderGuidanceRepository.findOrderGuidanceById(orderId);

        if (orderGuidance==null) {
            throw new ApiException("orderGuidance id not found");
        }

        return orderGuidance;
    }


    public List<OrderGuidance> retrieveAllbyStatus(String status){

        List<OrderGuidance> orderGuidance = orderGuidanceRepository.findAllByStatus(status);

        if (orderGuidance.isEmpty()) {
            throw new ApiException("OrderGuidance list with status "+status+" empty");
        }
        return orderGuidance;
    }

    //khaled
    //company customer
    public void commentOnOrderGuidance(Integer userId, Integer orderGuidanceId, String comment){
        OrderGuidance orderGuidance = orderGuidanceRepository.findOrderGuidanceById(orderGuidanceId);
        if(orderGuidance == null){
            throw new ApiException("order plant not found");
        }
        if(orderGuidance.getCustomer().getId()!=userId && orderGuidance.getCompany().getId()!=userId){
            throw new ApiException("invalid comment request");
        }
        if(!orderGuidance.getStatus().equalsIgnoreCase("finished")){
            throw new ApiException("once the order is finished you can comment");
        }

        orderGuidance.setComment(comment);
        orderGuidanceRepository.save(orderGuidance);
    }

    //sara
    public void evaluationOnOrderGuidance(Integer userId, Integer orderId, Double evaluation){
        Double evaluate=0.0;
        OrderGuidance orderGuidance = orderGuidanceRepository.findOrderGuidanceById(orderId);
        if(orderGuidance == null){
            throw new ApiException("order plant not found");
        }
        if(!orderGuidance.getStatus().equalsIgnoreCase("finished")){
            throw new ApiException("invalid evaluation request");
        }
        if(orderGuidance.getCustomer().getId()==userId || orderGuidance.getCompany().getId()==userId){
            orderGuidance.setEvaluation(evaluation);
            orderGuidanceRepository.save(orderGuidance);
        }


        List<OrderGuidance> orderGuidances = orderGuidanceRepository.findOrderGuidanceBySpecialistId(orderGuidance.getSpecialist().getId());
        for (OrderGuidance orderGuidance1 : orderGuidances) {
            if(orderGuidance1.getStatus().equalsIgnoreCase("finished") && orderGuidance1.getEvaluation()!= null){
                evaluate+=orderGuidance1.getEvaluation();
            }
        }

        orderGuidance.getSpecialist().setNumberOfEvaluation(orderGuidance.getSpecialist().getNumberOfEvaluation()+1);
        orderGuidance.getSpecialist().setEvaluation((evaluate/orderGuidance.getSpecialist().getNumberOfEvaluation()));
        orderGuidanceRepository.save(orderGuidance);
        specialistRepository.save(orderGuidance.getSpecialist());

    }

}
