package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.CompanyDTO;
import com.example.yourfarm.DTO.PlantDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ansi.AnsiOutput;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/company")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    Logger logger = LoggerFactory.getLogger(CompanyController.class);

//ADMIN
    @GetMapping("/companies")
    public ResponseEntity getAllCompany(@AuthenticationPrincipal User user){
        logger.info("get all company");
        return ResponseEntity.ok(companyService.getAllCompany());
    }
//ALL
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid CompanyDTO companyDTO){
        companyService.register(companyDTO);
        logger.info("company added");
        return ResponseEntity.ok(new ApiResponse("company created"));
    }
//COMPANY
    @PutMapping("/update")
    public ResponseEntity updateCompany(@AuthenticationPrincipal User user, @RequestBody @Valid CompanyDTO companyDTO){
        companyService.update(user.getId(), companyDTO);
        logger.info("update company");
        return ResponseEntity.ok(new ApiResponse("company updated"));
    }
    //ADMIN
    @DeleteMapping("/delete/{company_id}")
    public ResponseEntity deleteCompany(@PathVariable Integer company_id){
        companyService.deleteCompany(company_id);
        logger.info("company deleted");
        return ResponseEntity.ok(new ApiResponse("company deleted"));
    }
    //-------------------------------------   end CRUD  ---------------------------



}
