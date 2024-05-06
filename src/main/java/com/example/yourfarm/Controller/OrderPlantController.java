package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.PlantDTO;
import com.example.yourfarm.Model.Company;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.OrderPlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-plant")
@RequiredArgsConstructor
public class OrderPlantController {
    private final OrderPlantService orderPlantService;
    Logger logger = LoggerFactory.getLogger(OrderFarmerController.class);

//ADMIN
    @GetMapping("/get")
    public ResponseEntity getOrderPlant(){
        logger.info("get OrderPlant");
        return ResponseEntity.ok(orderPlantService.getAllOrderPlant());
    }

        //COMPANY - CUSTOMER  //Sara
    @PostMapping("/order-plant")
    public ResponseEntity OrderPlant(@AuthenticationPrincipal User user, @RequestBody @Valid PlantDTO plantDTO){
      orderPlantService.OrderPlant(user.getId(), plantDTO);
        return ResponseEntity.ok(new ApiResponse("Order successfully"));
    }

    //khaled //COMPANY - CUSTOMER
    @PutMapping("/order-plant-cancel/{order_plant_id}")
    public ResponseEntity cancelOrderPlant(@AuthenticationPrincipal User user, @PathVariable Integer order_plant_id){
        orderPlantService.cancelOrderPlant(user.getId(), order_plant_id);
        logger.info("order plant canceled");
        return ResponseEntity.ok(new ApiResponse("order canceled"));
    }

    //-------------------------------------   end CRUD  ---------------------------

    //Farm  //Sara
    @GetMapping("/new-plant-orders")
    public ResponseEntity NewPlantOrders(@AuthenticationPrincipal Farm farm){
        logger.info("get all New Plant Orders");
        return ResponseEntity.ok(orderPlantService.NewPlantOrders(farm.getId()));
    }

    //COMPANY  //Sara
    @GetMapping("/current-plant-orders")
    public ResponseEntity currentPlantOrders(@AuthenticationPrincipal Company company){
        logger.info("get all current Plant Orders");
        return ResponseEntity.ok(orderPlantService.currentPlantOrders(company.getId()));
    }
    //COMPANY  //Sara
    @GetMapping("/previous-plant-orders")
    public ResponseEntity previousPlantOrders(@AuthenticationPrincipal Company company){
        logger.info("get all previous Plant Orders");
        return ResponseEntity.ok(orderPlantService.previousPlantOrders(company.getId()));
    }


    //KHLOUD
    @PutMapping("/update-order-plant/{order_id}")
    public ResponseEntity updateStatusOrderPlant(@AuthenticationPrincipal User user, @PathVariable Integer order_id){
        logger.info("updateStatusOrderPlant");
        orderPlantService.updateStatusOrderPlant(user.getId(),order_id);
        return ResponseEntity.status(200).body(new ApiResponse("update Status Order Plant Successful"));
    }
    //kholud 15
    @PutMapping("/reject-order-plant/{order_id}")
    public ResponseEntity rejectOrderPlant(@AuthenticationPrincipal User user,@PathVariable Integer order_id){
        logger.info("rejectOrderPlant");
        orderPlantService.rejectOrderPlant(user.getId(),order_id);
        return ResponseEntity.status(200).body(new ApiResponse("reject Order Plant Successful"));
    }

    //kholud 16
    @PostMapping("/accept-order-plant/{order_id}")
    public ResponseEntity acceptOrderPlant(@AuthenticationPrincipal User user, @PathVariable Integer order_id) {
        logger.info("acceptOrderPlant");
        orderPlantService.acceptOrderPlant(user.getId(), order_id);
        return ResponseEntity.status(200).body(new ApiResponse("accept Order Plant Successful"));

    }


    //khaled
    //company customer
    @PutMapping("/comment/{order_plant_id}/{comment}")
    public ResponseEntity commentOnOrderPlant(@AuthenticationPrincipal User user, @PathVariable Integer order_plant_id, @PathVariable String comment){
        logger.info("comment on orderPlant");
        orderPlantService.commentOnOrderPlant(user.getId(),order_plant_id, comment);
        return ResponseEntity.ok(new ApiResponse("commented"));
    }

     //Sara
    @PutMapping("/evaluation-on-order-plant/{order_plant_id}/{evaluation}")
    public ResponseEntity evaluationOnOrderPlant(@AuthenticationPrincipal User user, @PathVariable Integer orderPlantId,@PathVariable Double evaluation ){
        logger.info("Add evaluation On Order Plant");
        orderPlantService.evaluationOnOrderPlant(user.getId(),orderPlantId,evaluation);
        return ResponseEntity.ok("Add evaluation On Order Plant");
    }
}
