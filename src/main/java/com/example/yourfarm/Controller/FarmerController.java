package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.FarmerDTO;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.Farmer;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.FarmerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/farmer")
@RequiredArgsConstructor
public class FarmerController {

    private final FarmerService farmerService;

    Logger logger = LoggerFactory.getLogger(FarmController.class);
//ALL
    @GetMapping("/farmers")
    public ResponseEntity getAllFarmers(){
        logger.info("get all farmers");
        return ResponseEntity.ok(farmerService.getAllFarmer());
    }
//ALL
    @PostMapping("/register")
    public ResponseEntity registerFarmer(@RequestBody @Valid FarmerDTO farmerDTO){
        farmerService.register(farmerDTO);
        logger.info("farmer registered");
        return ResponseEntity.ok(new ApiResponse("farmer registered"));
    }
//FARMER
    @PutMapping("/update")
    public ResponseEntity updateFarmer(@AuthenticationPrincipal User user, @RequestBody @Valid FarmerDTO farmerDTO){
        farmerService.update(user.getFarmer().getId(), farmerDTO);
        logger.info("farmer updated");
        return ResponseEntity.ok(new ApiResponse("farmer updated"));
    }
//ADMIN
    @DeleteMapping("/delete/{farmer_id}")
    public ResponseEntity deleteFarmer(@AuthenticationPrincipal User user, @PathVariable Integer farmer_id){
        farmerService.deleteFarmer(farmer_id);
        logger.info("farmer_id");
        return ResponseEntity.ok(new ApiResponse("farmer deleted"));
    }

    //-------------------------------------   end CRUD  ---------------------------

    //all //khaled
    @GetMapping("/comments/{farmer_id}")
    public ResponseEntity viewComments(@PathVariable Integer farmer_id){
        logger.info("farm comments");
        return ResponseEntity.ok(farmerService.viewCommentFarmer(farmer_id));
    }

    //All //sara
    @GetMapping("/get-best-evaluation")
    public ResponseEntity getBestEvaluationForFarmer(){
        logger.info("get bestEvaluationForFarmer");
        return ResponseEntity.ok(farmerService.bestEvaluation());
    }
}
