package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.Model.Contract;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.OrderGuidance;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.CompanyService;
import com.example.yourfarm.Service.ContractService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/contract")
@RequiredArgsConstructor
public class ContractController {

    private final ContractService contractService;
    private final CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(ContractController.class);

//ADMIN
    @GetMapping("/contract")
    public ResponseEntity getAllContract(){
        logger.info("get all contracts");
        return ResponseEntity.ok(contractService.getAllContract());
    }
    //company //khaled
    @PostMapping("/create-contract")
    public ResponseEntity createContract(@AuthenticationPrincipal User user, @RequestBody @Valid Contract contract){
        contractService.createContract(user.getId(), contract);
        logger.info("company created contract");
        return ResponseEntity.ok(new ApiResponse("contract created"));
    }
    //-------------------------------------   end CRUD  ---------------------------

    //Farm
    @PutMapping("/accept-contract/{order_id}")
    public ResponseEntity acceptContract(@AuthenticationPrincipal User user, @PathVariable Integer order_id){
        contractService.acceptContract(user.getId(), order_id);
        return ResponseEntity.status(200).body("accept contract Successfully");
    }

     //farm //sara
    @GetMapping("/new-contract")
    public ResponseEntity NewContract(@AuthenticationPrincipal Farm farm){
        logger.info("get all New Contract");
        return ResponseEntity.ok(contractService.NewContract());
    }

    //Farm //Sara
    @GetMapping("/current-contract")
    public ResponseEntity currentContract(@AuthenticationPrincipal Farm farm){
        logger.info("get all current Contract");
        return ResponseEntity.ok(contractService.currentContract(farm.getId()));
    }

    //Farm //Sara
    @GetMapping("/previous-contract")
    public ResponseEntity previousContract(@AuthenticationPrincipal Farm farm){
        logger.info("get all previous Contract");
        return ResponseEntity.ok(contractService.previousContract(farm.getId()));
    }
    //company
    //khaled
    @PutMapping("/cancel/{contract_id}")
    public ResponseEntity cancelContract(@AuthenticationPrincipal User user, @PathVariable Integer contract_id){
        contractService.cancelContract(user.getId(), contract_id);
        logger.info("company canceled contract");
        return ResponseEntity.ok(new ApiResponse("contract canceled"));
    }



}
