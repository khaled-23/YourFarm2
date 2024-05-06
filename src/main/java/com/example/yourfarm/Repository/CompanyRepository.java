package com.example.yourfarm.Repository;

import com.example.yourfarm.Model.Company;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompanyRepository extends JpaRepository<Company,Integer> {
    Company findCompanyById(Integer id);

    Company findCompanyByUserId(Integer id);

}
