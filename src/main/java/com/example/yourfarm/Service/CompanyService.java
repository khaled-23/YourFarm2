package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.DTO.CompanyDTO;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.DTO.PlantDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CompanyService {
    private final CompanyRepository companyRepository;
    private final AuthRepository authRepository;
    private final AuthService authService;
    private final OrderPlantRepository orderPlantRepository;
    private final OrderGuidanceRepository orderGuidanceRepository;
    private final OrderFarmerRepository orderFarmerRepository;
    private final ContractRepository contractRepository;
    private final PlantRepository plantRepository;
    private final FarmerRepository farmerRepository;
    private final SpecialistRepository specialistRepository;


    //ADMIN
    public List<Company> getAllCompany(){
        if (companyRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return companyRepository.findAll();
    }

    //COMPANY
    public void register(CompanyDTO company) {
        User user =new User(null,company.getUsername(),company.getPassword(),"COMPANY",company.getName(),company.getEmail(),company.getPhoneNumber(), company.getImage(), null,null,null,null,null);
        authService.register(user);


        Company company1= new Company(user.getId(), company.getRegion(), company.getNationalAddress(), company.getCommercialRegister(),null,null,null,null,user);
        companyRepository.save(company1);

    }

    //COMPANY
    public void update(Integer userId, CompanyDTO company) {
        User user = authRepository.findUserById(userId);
        Company company1 = companyRepository.findCompanyByUserId(userId);

        String hash = new BCryptPasswordEncoder().encode(company.getPassword());

        user.setName(company.getName());
        user.setEmail(company.getEmail());
        user.setPhoneNumber(company.getPhoneNumber());
        user.setPassword(hash);
        user.setUsername(company.getUsername());
        authRepository.save(user);

        company1.setUser(user);
        companyRepository.save(company1);
    }

    //ADMIN
    public void deleteCompany(Integer companyId) {
        Company company = companyRepository.findCompanyById(companyId);

        if (company == null) {
            throw new ApiException(" Company not found");
        }
        companyRepository.delete(company);
    }

    //-------------------------------------   end CRUD  ---------------------------

    }

