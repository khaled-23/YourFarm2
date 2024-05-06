package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.OrderGuidance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderGuidanceRepository extends JpaRepository<OrderGuidance,Integer> {
    List<OrderGuidance> findOrderGuidanceBySpecialistId(Integer specialistId);
    List<OrderGuidance> findOrderGuidanceByCompanyId(Integer companyId);
    List<OrderGuidance> findOrderGuidanceByCustomerId(Integer customerId);

    OrderGuidance findOrderGuidanceById(Integer orderGuidanceId);
    List<OrderGuidance> findAllByStatus(String status);
    @Query("select a from OrderPlant a where a.status='finished'")
    List<OrderGuidance> findOrderGuidance();
}
