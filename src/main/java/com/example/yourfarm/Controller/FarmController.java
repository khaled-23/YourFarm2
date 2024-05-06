package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.FarmDTO;
import com.example.yourfarm.Model.Company;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.FarmService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farm")
@RequiredArgsConstructor
public class FarmController {
    private final FarmService farmService;

    Logger logger = LoggerFactory.getLogger(FarmController.class);

//ALL
    @GetMapping("/farms")
    public ResponseEntity getAllFarm(){
        logger.info("get all farm");
        return ResponseEntity.ok(farmService.getAllFarm());
    }

//ALL
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid FarmDTO farmDTO){
        farmService.register(farmDTO);
        logger.info("farm registered");
        return ResponseEntity.ok(new ApiResponse("farm registered"));
    }

//FARM
    @PutMapping("/update")
    public ResponseEntity updateFarm(@AuthenticationPrincipal User user, @RequestBody @Valid FarmDTO farmDTO){
        farmService.update(user.getFarm().getId(), farmDTO);
        logger.info("farm updated");
        return ResponseEntity.ok(new ApiResponse("farm updated"));
    }
//ADMIN
    @DeleteMapping("/delete/{farm_id}")
    public ResponseEntity deleteFarm(@AuthenticationPrincipal User user, @PathVariable Integer farm_id){
        farmService.deleteFarm(farm_id);
        logger.info("farm deleted");
        return ResponseEntity.ok(new ApiResponse("farm deleted"));
    }
//-------------------------------------   end CRUD  ---------------------------



    //all //khaled
    @GetMapping("/comments/{farm_id}")
    public ResponseEntity viewComments(@PathVariable Integer farm_id){
        logger.info("farm comments");
        return ResponseEntity.ok(farmService.viewCommentFarm(farm_id));
    }

    //All //sara
    @GetMapping("/get-best-evaluation")
    public ResponseEntity getBestEvaluationForFarm(){
        logger.info("get bestEvaluationForFarm");
        return ResponseEntity.ok(farmService.bestEvaluation());
    }

    //All //sara
    @GetMapping("/get-best-sales")
    public ResponseEntity getBestSalesForFarm(){
        logger.info("get bestSalesForFarm");
        return ResponseEntity.ok(farmService.bestSales());
    }
}
