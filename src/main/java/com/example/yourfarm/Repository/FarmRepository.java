package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.Farmer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FarmRepository extends JpaRepository<Farm,Integer> {
    Farm findFarmById(Integer id);
    Farm findFarmByName(String name);
    List<Farm> findAllByRegion(String region);
    @Query("SELECT p FROM Farm p ORDER BY p.evaluation DESC")
    List<Farm> searchTopByEvaluation();

    @Query("SELECT p FROM Farm p ORDER BY p.sales DESC")
    List<Farm> searchTopBySales();
}
