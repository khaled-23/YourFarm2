package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.FarmerDTO;
import com.example.yourfarm.Model.Company;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.OrderFarmer;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.OrderFarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-farmer")
@RequiredArgsConstructor
public class OrderFarmerController {
    private final OrderFarmerService orderFarmerService;
    Logger logger = LoggerFactory.getLogger(OrderFarmerController.class);
//ADMIN
    @GetMapping("/get")
    public ResponseEntity getOrderFarmer(){
        logger.info("get all Order Farmer ");
        return ResponseEntity.ok(orderFarmerService.getAllOrderFarmer());
    }

    //COMPANY //khaled
    @PostMapping("/request-farmer/{farmer_id}")
    public ResponseEntity requestFarmer(@AuthenticationPrincipal User user, @PathVariable Integer farmer_id, @RequestBody @Valid OrderFarmer order_farmer){
        orderFarmerService.requestFarmer(user.getId(), farmer_id,order_farmer);
        logger.info("company requested farmer");
        return  ResponseEntity.ok(new ApiResponse("request farmer completed"));
    }
    //company //khaled
    @PutMapping("/order-farmer-cancel/{order_farmer_id}")
    public ResponseEntity cancelOrderFarmer(@AuthenticationPrincipal User user, @PathVariable Integer order_farmer_id){
       orderFarmerService.cancelOrderFarmer(user.getId(), order_farmer_id);
        logger.info("cancel order farmer");
        return ResponseEntity.ok(new ApiResponse("order canceled"));
    }
    //-------------------------------------   end CRUD  ---------------------------

    //Farm  //Sara
    @GetMapping("/new-orders-to-farmer")
    public ResponseEntity NewOrdersToFarmer(@AuthenticationPrincipal  User user){
        logger.info("get all New Orders To Farmer");
        return ResponseEntity.ok(orderFarmerService.NewOrdersToFarmer(user.getId()));
    }

    //Farm //Sara
    @GetMapping("/current-orders")
    public ResponseEntity currentContract(@AuthenticationPrincipal User user){
        logger.info("get all current Orders To Farmer");
        return ResponseEntity.ok(orderFarmerService.currentOrdersToFarmer(user.getId()));
    }

    //Farm  //Sara
    @GetMapping("/previous-orders")
    public ResponseEntity previousOrdersToFarmer(@AuthenticationPrincipal User user){
        logger.info("get all previous Orders To Farmer");
        return ResponseEntity.ok(orderFarmerService.previousOrdersToFarmer(user.getId()));
    }

    @PostMapping("/update-order-farmer/{order_id}")
    public ResponseEntity updateStatusOrderFarmer(@AuthenticationPrincipal User user, @PathVariable Integer order_id){
        orderFarmerService.updateStatusOrderFarmer(user.getId(),order_id);
        return ResponseEntity.status(200).body(new ApiResponse("update Status Order farmer Successful"));
    }

    //kholud 5
    @PostMapping("/reject-order-farmer/{order_id}")
    public ResponseEntity rejectOrderFarmer(@AuthenticationPrincipal  User user,@PathVariable Integer order_id){
        orderFarmerService.rejectOrderFarmer(user.getId(),order_id);
        return ResponseEntity.status(200).body(new ApiResponse("reject Order farmer Successful"));

    }

    //kholud 6
    @PostMapping("/accept-order-farmer/{order_id}")
    public ResponseEntity acceptOrderFarmer(@AuthenticationPrincipal User user, @PathVariable Integer order_id){
        orderFarmerService.acceptOrderFarmer(user.getId(),order_id);
        return ResponseEntity.status(200).body(new ApiResponse("accept Order farmer Successful"));

    }//Done

//

    //khaled
    //company customer
    @PutMapping("/comment/{order_farmer_id}/{comment}")
    public ResponseEntity commentOnOrderFarmer(@AuthenticationPrincipal User user, @PathVariable Integer order_farmer_id, @PathVariable String comment){
        logger.info("comment on orderFarmer");
        orderFarmerService.commentOnOrderFarmer(user.getId(),order_farmer_id, comment);
        return ResponseEntity.ok(new ApiResponse("commented"));
    }

    //Sara
    @PutMapping("/evaluation-on-order-farmer/{order_farmer_id}/{evaluation}")
    public ResponseEntity evaluationOnOrderFarmer(@AuthenticationPrincipal User user, @PathVariable Integer order_farmer_id,@PathVariable Double evaluation ){
        logger.info("Add evaluation On Order Farmer");
        orderFarmerService.evaluationOnOrderFarmer(user.getId(),order_farmer_id,evaluation);
        return ResponseEntity.ok("Add evaluation On Order Farmer");
    }
}
