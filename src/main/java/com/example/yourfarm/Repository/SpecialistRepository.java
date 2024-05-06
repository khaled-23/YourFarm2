package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.Specialist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist,Integer> {
    Specialist findSpecialistById(Integer id);
    List<Specialist> findAllByRegion(String region);

    @Query("SELECT p FROM Specialist p ORDER BY p.evaluation DESC")
    List<Specialist> searchTopByEvaluation();

}
