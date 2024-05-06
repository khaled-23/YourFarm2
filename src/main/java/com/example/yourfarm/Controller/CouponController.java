package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.Model.Coupon;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/coupon")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;
    Logger logger = LoggerFactory.getLogger(ContractController.class);

  //  ADMIN
    @GetMapping("/coupons")
    public ResponseEntity getAllCoupon(){
        logger.info("get all Coupon");
        return ResponseEntity.ok(couponService.getAllCoupon());
    }

    //ALL //sara
    @PostMapping("/add/{value}")
    public ResponseEntity createCoupon(@AuthenticationPrincipal User user, @PathVariable Integer value){
        couponService.createCoupon(user.getId(),value);
        return ResponseEntity.ok(new ApiResponse("create Coupon"));
    }
}
