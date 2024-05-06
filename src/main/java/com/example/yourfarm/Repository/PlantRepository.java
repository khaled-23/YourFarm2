package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.Plant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlantRepository extends JpaRepository<Plant,Integer> {
    Plant findPlantById(Integer id);
List<Plant> findPlantByFarm(Farm farm);
Plant findPlantByName(String name);
List<Plant> findPlantByType(String type);


}
