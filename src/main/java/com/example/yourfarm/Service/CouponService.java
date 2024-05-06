package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.Model.Company;
import com.example.yourfarm.Model.Contract;
import com.example.yourfarm.Model.Coupon;
import com.example.yourfarm.Repository.CompanyRepository;
import com.example.yourfarm.Repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CouponService {
    private final CouponRepository couponRepository;

    public List<Coupon> getAllCoupon(){
        if (couponRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return couponRepository.findAll();
    }
    //sara
    public StringBuilder createCoupon(Integer userId, Integer value){
        Coupon coupon = new Coupon();
        Random random = new Random();

        StringBuilder randomVariable = new StringBuilder();
            for (int i = 0; i < 7; i++) {
                randomVariable.append(random.nextInt(10));
            }
            coupon.setCouponNumber(randomVariable);
        coupon.setStatus("valid");
        coupon.setValue(value);
         return randomVariable;
    }
}
