package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract,Integer> {
    List<Contract> findContractByCompanyId(int companyId);
    List<Contract> findContractByFarmId(int farmId);

    @Query("select a from Contract a where a.status='waiting'")
    List<Contract> newContract();

    Contract findContractById(Integer id);
}
