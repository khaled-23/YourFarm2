package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.DTO.PlantDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Service.CustomerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;
  Logger logger = LoggerFactory.getLogger(CustomerController.class);

//ADMIN
    @GetMapping("/customers")
    public ResponseEntity getAllCustomer(){
        logger.info("get all customer");
        return ResponseEntity.ok(customerService.getAllCustomer());
    }

//ALL
    @PostMapping("/register")
    public ResponseEntity registerAsCustomer(@RequestBody @Valid CustomerDTO customerDTO){
        customerService.register(customerDTO);
        return ResponseEntity.ok(new ApiResponse("customer registered"));
    }

//CUSTOMER
    @PutMapping("/update")
    public ResponseEntity updateCustomer(@AuthenticationPrincipal User user, @RequestBody @Valid CustomerDTO customerDTO){
        customerService.update(user.getCustomer().getId(), customerDTO);
        logger.info("customer updated");
        return ResponseEntity.ok(new ApiResponse("customer update"));
    }
//ADMIN
    @DeleteMapping("/delete/{customer_id}")
    public ResponseEntity deleteCustomer(@PathVariable Integer customer_id){
        customerService.deleteCustomer(customer_id);
        logger.info("customer deleted");
        return ResponseEntity.ok(new ApiResponse("customer deleted"));
    }
//-------------------------------------   end CRUD  ---------------------------

    //khloud
    @GetMapping("/search-farms-near")
    public ResponseEntity searchFarmsNear(@AuthenticationPrincipal User user){
        logger.info("searchFarmsNear");
        return ResponseEntity.status(200).body(customerService.searchFarmsNear(user.getId()));
    }

    //Kholud 2
    @GetMapping("/search-farmer-near")
    public ResponseEntity searchFarmerNear(@AuthenticationPrincipal User user){
        logger.info("searchFarmerNear");
        return ResponseEntity.status(200).body(customerService.searchFarmerNear(user.getId()));

    }

    //Kholud 3
    @GetMapping("/search-specialists-near")
    public ResponseEntity searchSpecialistsNear(@AuthenticationPrincipal User user){
        logger.info("searchSpecialistsNear");
        return ResponseEntity.status(200).body(customerService.searchSpecialistsNear(user.getId()));

    }


}
