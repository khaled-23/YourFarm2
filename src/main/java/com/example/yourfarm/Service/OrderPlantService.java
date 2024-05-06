package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.DTO.PlantDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class    OrderPlantService {

    private final OrderPlantRepository orderPlantRepository;

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final PlantRepository plantRepository;
    private final CompanyRepository companyRepository;
    private final FarmRepository farmRepository;
    private final OrderItemRepository orderItemRepository;


    //ADMIN
    public List<OrderPlant> getAllOrderPlant(){
        if (orderPlantRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return orderPlantRepository.findAll();
    }


    //CUSTOMER -COMPANY
//sara
    public OrderPlant OrderPlant(Integer userId, PlantDTO plantDTO) {

        User user = authRepository.findUserById(userId);
        List<OrderItem> orderItems = new ArrayList<>();
        OrderPlant orderPlant = new OrderPlant();

        OrderItem orderItem = plantDTO.getOrderItems().get(0);

      Plant plant= plantRepository.findPlantById(orderItem.getPlantId());
        for (OrderItem p : plantDTO.getOrderItems()) {
            Plant plant1= plantRepository.findPlantById(orderPlant.getId());
            if (plant.getFarm().getId() != plant1.getFarm().getId()){
                throw new ApiException(" order can not completed");
            }
        }
           plant.getFarm().setSales(plant.getFarm().getSales() + 1);
        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orderPlant.setCustomer(customer);
        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orderPlant.setCompany(company);
        }

        orderPlant.setReceivedDateAndTime(plantDTO.getReceivedDateAndTime());
        orderPlant.setStatus("waiting");
        orderPlant.setDateOfOrder(LocalDate.now());
        orderPlant.setPhoneNumber(plantDTO.getPhoneNumber());
        orderPlant.setRegion(plantDTO.getRegion());
        orderPlant.setNationalAddress(plantDTO.getNationalAddress());
        orderPlant.setComment("null");
        orderPlant.setEvaluation(0.0);
        orderPlant.setTotalPrice(0);
        Integer totalPrice = 0;
       // orderPlantRepository.save(orderPlant);

        for (OrderItem p : plantDTO.getOrderItems()) {
            if (plantRepository.findPlantById(p.getPlantId()) == null)
                throw new ApiException(" one plant not found");
            else {
                Plant plant2 = plantRepository.findPlantById(p.getPlantId());
                if (plant2.getQuantity()<p.getQuantity()){
                    throw new ApiException(" Quantity not found");
                }
                plant.setQuantity(plant2.getQuantity()-p.getQuantity());
                plantRepository.save(plant2);
                OrderItem orderItem1 = new OrderItem(null,p.getPlantId(), p.getQuantity(), plant2.getName(), plant2.getPrice(), orderPlant);
                orderItems.add(orderItem1);
                orderItemRepository.save(orderItem1);
                totalPrice=totalPrice+(plant2.getPrice()*p.getQuantity());
            }

            orderPlant.setOrderItems(orderItems);
            orderPlant.setTotalPrice(totalPrice);
            orderPlantRepository.save(orderPlant);
        }
        return orderPlant;
    }

    //khaled
    //customer  - company
    public void cancelOrderPlant(Integer userId, Integer orderPlantId){
        User user = authRepository.findUserById(userId);
        List<OrderPlant> orderPlant = new ArrayList<>();
        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orderPlant = orderPlantRepository.findOrdersByCustomerId(customer.getId());

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orderPlant = orderPlantRepository.findOrdersByCompanyId(company.getId());
        }

        for (OrderPlant orderPlant1 : orderPlant) {
            if (orderPlant1.getId().equals(orderPlantId)){
                if(orderPlant1.getStatus().equalsIgnoreCase("waiting")){
                    orderPlant1.setStatus("Canceled");}}
        }
    }

//-------------------------------------   end CRUD  ---------------------------

    //sara
    public List<OrderPlant> NewPlantOrders(Integer farmId) {
        ArrayList<OrderPlant> orders2 = new ArrayList<>();
        List<OrderPlant> orders3 = orderPlantRepository.findOrdersByFarmId(farmId);

        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderPlant orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("Waiting")){
                orders2.add(orders1);
            }}

        return orders2;
    }
//sara
    public List<OrderPlant> currentPlantOrders(Integer userId) {
        ArrayList<OrderPlant> orders2 = new ArrayList<>();
        List<OrderPlant> orders3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orders3=orderPlantRepository.findOrdersByCustomerId(customer.getId());

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orders3 = orderPlantRepository.findOrdersByCompanyId(company.getId());

        }
        else if (user.getRole().equalsIgnoreCase("FARM")) {
           Farm farm= farmRepository.findFarmById(userId);
            orders3 = orderPlantRepository.findOrdersByFarmId(farm.getId());

        }
        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderPlant orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("Ready to deliver") || orders1.getStatus().equalsIgnoreCase("accepted")  ){
                orders2.add(orders1);
            }}

        return orders2;
    }

//sara
    public List<OrderPlant> previousPlantOrders(Integer userId) {
        List<OrderPlant> orders3 = new ArrayList<>();
        User user = authRepository.findUserById(userId);

        if (user.getRole().equalsIgnoreCase("CUSTOMER")) {
            Customer customer = customerRepository.findCustomerById(userId);
            orders3=orderPlantRepository.findOrdersByCustomerId(customer.getId());

        }else if (user.getRole().equalsIgnoreCase("COMPANY")) {
            Company company = companyRepository.findCompanyById(userId);
            orders3 = orderPlantRepository.findOrdersByCompanyId(company.getId());
        }
        else if (user.getRole().equalsIgnoreCase("FARM")) {
            Farm farm= farmRepository.findFarmById(userId);
            orders3 = orderPlantRepository.findOrdersByFarmId(farm.getId());
        }
        ArrayList<OrderPlant> orders2 = new ArrayList<>();


        if (orders3== null) {
            throw new ApiException(" orders not found");
        }
        for (OrderPlant orders1 : orders3) {
            if (orders1.getStatus().equalsIgnoreCase("Delivered")|| orders1.getStatus().equalsIgnoreCase("Rejected")|| orders1.getStatus().equalsIgnoreCase("Canceled")){
                orders2.add(orders1);
            }}

        return orders2;
    }
//KHLOUD
public void updateStatusOrderPlant(Integer farmId, Integer orderId) {
    OrderPlant orderPlant = orderPlantRepository.findOrderPlantById(orderId);

    if (orderPlant == null) {
        throw new ApiException("orderPlan id not found");
    }
    if (orderPlant.getFarm().getId()!= farmId) {
        throw new ApiException("orderPlant not for you");
    }

    if (orderPlant.getStatus().equalsIgnoreCase("Rejected")) {
        throw new ApiException("orderPlant is rejected can not changed");
    }
    if (orderPlant.getStatus().equalsIgnoreCase("Delivered")) {
        throw new ApiException("orderPlant is delivered already");
    }

    else if (orderPlant.getStatus().equalsIgnoreCase("accepted")) {
        orderPlant.setStatus("Ready to deliver");
    } else if (orderPlant.getStatus().equalsIgnoreCase("Ready to deliver")) {
        orderPlant.setStatus("Delivered");
    }
    orderPlantRepository.save(orderPlant);
}


    //KHLOUD
    public void rejectOrderPlant(Integer farmId, Integer orderId) {
        OrderPlant orderPlant = orderPlantRepository.findOrderPlantById(orderId);

        if (orderPlant == null) {
            throw new ApiException("orderPlant id not found");
        }

        if (orderPlant.getFarm().getId()!= farmId) {
            throw new ApiException("orderPlant not for you");
        }

        orderPlant.setStatus("Rejected");
        orderPlantRepository.save(orderPlant);

    }

    //KHLOUD
    ///3 kh
    public void acceptOrderPlant(Integer farmId, Integer orderId) {
        OrderPlant orderPlant = orderPlantRepository.findOrderPlantById(orderId);

        if (orderPlant == null) {
            throw new ApiException("orderPlant id not found");
        }

        if (orderPlant.getFarm().getId() != farmId)
            throw new ApiException("orderPlant not for you");
        orderPlant.setStatus("accepted");
        orderPlantRepository.save(orderPlant);

    }


    //khloud
    public OrderPlant retrieveById( Integer orderId){
        OrderPlant orderPlant = orderPlantRepository.findOrderPlantById(orderId);

        if (orderPlant == null) {
            throw new ApiException("orderPlant id not found");
        }
        return orderPlant;

    }

    //khloud
    public List<OrderPlant> retrieveAllbyStatus(String status){

        List<OrderPlant> orderPlant = orderPlantRepository.findAllByStatus(status);

        if (orderPlant.isEmpty()) {
            throw new ApiException("orderPlant list with status "+status+" empty");
        }
        return orderPlant;

    }

    //khaled
    //customer company
    public void commentOnOrderPlant(Integer userId, Integer orderId, String comment){
        OrderPlant orderPlant = orderPlantRepository.findOrderPlantById(orderId);
        if(orderPlant == null){
            throw new ApiException("order plant not found");
        }
        if(orderPlant.getCustomer().getId()!=userId && orderPlant.getCompany().getId()!=userId){
            throw new ApiException("invalid comment request");
        }
        if(!orderPlant.getStatus().equalsIgnoreCase("finished")){
            throw new ApiException("once the order is finished you can comment");
        }
        orderPlant.setComment(comment);
        orderPlantRepository.save(orderPlant);
    }

    //sara
    public void evaluationOnOrderPlant(Integer userId, Integer orderId, Double evaluation){
        Double evaluate=0.0;
        OrderPlant orderPlant = orderPlantRepository.findOrderPlantById(orderId);
        if(orderPlant == null){
            throw new ApiException("order plant not found");
        }
        if(!orderPlant.getStatus().equalsIgnoreCase("Delivered")){
            throw new ApiException("invalid evaluation request");
        }
        if(orderPlant.getCustomer().getId()==userId || orderPlant.getCompany().getId()==userId){
            orderPlant.setEvaluation(evaluation);
            orderPlantRepository.save(orderPlant);
        }


        List<OrderPlant> orderPlants = orderPlantRepository.findOrdersByFarmId(orderPlant.getFarm().getId());
            for (OrderPlant orderPlant1 : orderPlants) {
                if(orderPlant1.getStatus().equalsIgnoreCase("Delivered") && orderPlant1.getEvaluation()!= null){
                    evaluate+=orderPlant1.getEvaluation();
                }
            }

            orderPlant.getFarm().setNumberOfEvaluation(orderPlant.getFarm().getNumberOfEvaluation()+1);
            orderPlant.getFarm().setEvaluation((evaluate/orderPlant.getFarm().getNumberOfEvaluation()));
            orderPlantRepository.save(orderPlant);
            farmRepository.save(orderPlant.getFarm());

    }
}
