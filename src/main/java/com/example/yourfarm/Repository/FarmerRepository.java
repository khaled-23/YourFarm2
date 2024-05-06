package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmerRepository extends JpaRepository<Farmer,Integer> {
    Farmer findFarmerById(Integer id);
    List<Farmer> findAllByRegion(String region);

    @Query("SELECT p FROM Farmer p ORDER BY p.evaluation DESC")
    List<Farmer> searchTopByEvaluation();
}
