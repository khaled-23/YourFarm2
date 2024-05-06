package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.DTO.PlantDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.*;
import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final AuthRepository authRepository;
    private final AuthService authService;
    private final OrderPlantRepository orderPlantRepository;
    private final OrderFarmerRepository orderFarmerRepository;
    private final OrderGuidanceRepository orderGuidanceRepository;
private final PlantRepository plantRepository;
    private final SpecialistRepository specialistRepository;
    private final FarmerRepository farmerRepository;
    private final FarmRepository farmRepository;
    private final CompanyRepository companyRepository;


    //ADMIN
    public List<Customer> getAllCustomer(){
        if (customerRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return customerRepository.findAll();
    }

    //CUSTOMER
    public void register( CustomerDTO customer){
        User user =new User(null,customer.getUsername(),customer.getPassword(),"CUSTOMER",customer.getName(),customer.getEmail(),customer.getPhoneNumber(), customer.getImage(), null,null,null,null,null);
        authService.register(user);

        Customer customer1= new Customer(null, customer.getRegion(),customer.getNationalAddress() ,null,null,null,null);
        customer1.setUser(user);
        customerRepository.save(customer1);


    }

    //CUSTOMER
    public void update(Integer customerId, CustomerDTO customer) {
        User user = authRepository.findUserById(customerId);
        Customer customer1= customerRepository.findCustomerById(customerId);


        String hash = new BCryptPasswordEncoder().encode(customer.getPassword());

        user.setName(customer.getName());
        user.setEmail(customer.getEmail());
        user.setPhoneNumber(customer.getPhoneNumber());
        user.setPassword(hash);
        user.setUsername(customer.getUsername());
        authRepository.save(user);

        customer1.setUser(user);
        customerRepository.save(customer1);
    }

    //ADMIN
    public void deleteCustomer(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);
        if (customer == null) {
            throw new ApiException(" Customer not found");
        }
        customerRepository.delete(customer);
    }

    //-------------------------------------   end CRUD  ---------------------------

    //KHLOUD
    public List<Farm> searchFarmsNear(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new ApiException("Customer ID not found");
        }


        String Region = customer.getRegion();
        List<Farm> farmms1 = farmRepository.findAllByRegion(Region);
        if(farmms1.isEmpty()){
            throw new ApiException("The list is empty");
        }

        return farmms1;

    }

    //KHLOUD
    public List<Farmer> searchFarmerNear(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new ApiException("Customer ID not found");
        }

        String Region = customer.getRegion();
        List<Farmer> farmers = farmerRepository.findAllByRegion(Region);

        return farmers;


    }

    //KHLOUD
    public List<Specialist> searchSpecialistsNear(Integer customerId) {
        Customer customer = customerRepository.findCustomerById(customerId);

        if (customer == null) {
            throw new ApiException("Customer ID not found");
        }

        String Region = customer.getRegion();
        List<Specialist> specialists = specialistRepository.findAllByRegion(Region);

        return specialists;
    }

}
