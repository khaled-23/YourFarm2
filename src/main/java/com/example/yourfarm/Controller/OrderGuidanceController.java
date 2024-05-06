package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.OrderGuidance;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.OrderGuidanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/order-guidance")
@RequiredArgsConstructor
public class OrderGuidanceController {
    private final OrderGuidanceService orderGuidanceService;
    Logger logger = LoggerFactory.getLogger(OrderGuidanceController.class);

//ADMIN
    @GetMapping("/get")
    public ResponseEntity getOrderGuidance(){
        logger.info("get Order Guidance");
        return ResponseEntity.ok(orderGuidanceService.getAllOrderGuidance());
    }

    @PostMapping("/request-guidance/{specialist_id}")
    public ResponseEntity RequestGuidance(@AuthenticationPrincipal User user, @PathVariable Integer specialist_id, @RequestBody @Valid OrderGuidance orderGuidance){
        orderGuidanceService.requestGuidance(user.getId(), specialist_id,orderGuidance);
        logger.info("company requested guidance");
        return ResponseEntity.ok(new ApiResponse("request guidance complete"));
    }


    //company //khaled
    @PutMapping("/order_guidance_cancel/{order_guidance_id}")
    public ResponseEntity cancelOrderGuidance(@AuthenticationPrincipal User user, @PathVariable Integer order_guidance_id){
        orderGuidanceService.cancelOrderGuidance(user.getId(), order_guidance_id);
        logger.info("cancel order guidance");
        return ResponseEntity.ok(new ApiResponse("order canceled"));
    }


    //-------------------------------------   end CRUD  ---------------------------

   //Sara
    @GetMapping("/new-orders-to-specialist")
    public ResponseEntity NewOrdersToSpecialist(@AuthenticationPrincipal User user){
        logger.info("get all New Orders To Specialist");
        return ResponseEntity.ok(orderGuidanceService.NewOrdersToSpecialist(user.getId()));
    }
    //sara
    @GetMapping("/current-orders")
    public ResponseEntity currentContract(@AuthenticationPrincipal User user){
        logger.info("get all current Orders To Specialist");
        return ResponseEntity.ok(orderGuidanceService.currentOrdersToSpecialist(user.getId()));
    }

    //Farm  //Sara
    @GetMapping("/previous-orders")
    public ResponseEntity previousOrdersToFarmer(@AuthenticationPrincipal User user){
        logger.info("get all previous Orders To Specialist");
        return ResponseEntity.ok(orderGuidanceService.previousOrdersToSpecialist(user.getId()));
    }

    //kholud 9
    @PostMapping("/update-order-guidance/{orderId}")
    public ResponseEntity updateStatusOrderguidance(@AuthenticationPrincipal User user, @PathVariable Integer orderId){
        logger.info("updateStatusOrderguidance");
        orderGuidanceService.updateStatusOrderGuidance(user.getId(),orderId);
        return ResponseEntity.status(200).body(new ApiResponse("update Status Order farmer Successful"));
    }
    //kholud 10
    @PostMapping("/reject-order-guidance/{orderId}")
    public ResponseEntity rejectOrderGuidance(@AuthenticationPrincipal User user,@PathVariable Integer orderId){
        logger.info("rejectOrderGuidance");
        orderGuidanceService.rejectOrderGuidance(user.getId(),orderId);
        return ResponseEntity.status(200).body(new ApiResponse("reject Order guidance Successful"));
    }

    //kholud 11
    @PostMapping("/accept-order-guidance/{orderId}")
    public ResponseEntity accepctOrderGuidance(@AuthenticationPrincipal User user, @PathVariable Integer orderId){
        logger.info("accepctOrderGuidance");
        orderGuidanceService.accepctOrderGuidance(user.getId(),orderId);
        return ResponseEntity.status(200).body(new ApiResponse("accept Order guidance Successful"));

    }
    //kholud 12
    @GetMapping("/get-order-guidance/{orderId}")
    public ResponseEntity retrieveById(@AuthenticationPrincipal User user, @PathVariable Integer orderId){
        logger.info("retrieveById");
        return ResponseEntity.status(200).body(orderGuidanceService.retrieveById(orderId));
    }
    //kholud 13
    @GetMapping("/get-list-order-guidance/{status}")
    public ResponseEntity retrieveAllbyStatus(@AuthenticationPrincipal User user, @PathVariable String status){
        logger.info("retrieveAllbyStatus");
        return ResponseEntity.status(200).body(orderGuidanceService.retrieveAllbyStatus(status));
    }

    //khaled
    //company customer
    @PutMapping("/comment/{order_guidance_id}/{comment}")
    public ResponseEntity commentOnOrderGuidance(@AuthenticationPrincipal User user, @PathVariable Integer order_guidance_id, @PathVariable String comment){
        logger.info("comment on orderGuidance");
        orderGuidanceService.commentOnOrderGuidance(user.getId(),order_guidance_id, comment);
        return ResponseEntity.ok(new ApiResponse("commented"));
    }

    //Sara
    @PutMapping("/evaluation-on-order-guidance/{order_guidance_id}/{evaluation}")
    public ResponseEntity evaluationOnOrderGuidance(@AuthenticationPrincipal User user, @PathVariable Integer order_guidance_id,@PathVariable Double evaluation ){
        logger.info("Add evaluation On Order Guidance");
        orderGuidanceService.evaluationOnOrderGuidance(user.getId(),order_guidance_id,evaluation);
        return ResponseEntity.ok("Add evaluation On Order Guidance");
    }
}
