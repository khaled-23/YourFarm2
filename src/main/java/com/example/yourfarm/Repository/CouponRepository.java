package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.Company;

import com.example.yourfarm.Model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CouponRepository extends JpaRepository<Coupon,Integer> {
    Coupon findCouponById(Integer couponId);
}
