package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.Model.Company;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.Plant;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.PlantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/plant")
@RequiredArgsConstructor
public class PlantController {
    private final PlantService plantService;
    Logger logger = LoggerFactory.getLogger(PlantController.class);

//ALL
    @GetMapping("/plants")
    public ResponseEntity getAllPlant(){
        logger.info("get all Plant");
        return ResponseEntity.ok(plantService.getAllPlant());
    }

//FARM
    @PostMapping("/add")
    public ResponseEntity addPlant(@AuthenticationPrincipal User user, @RequestBody @Valid Plant plant){
        plantService.addPlant(user.getId(),plant);
        return ResponseEntity.ok(new ApiResponse("Add plant"));
    }

//FARM
    @PutMapping("/update/{plant_id}")
    public ResponseEntity updatePlant(@AuthenticationPrincipal Farm farm,@PathVariable Integer plant_id, @RequestBody @Valid Plant plant){
        plantService.update(farm.getId(),plant_id,plant);
        logger.info("Plant updated");
        return ResponseEntity.ok(new ApiResponse("Plant update"));
    }
//FARM
    @DeleteMapping("/delete/{plant_id}")
    public ResponseEntity deletePlant(@AuthenticationPrincipal Farm farm,@PathVariable Integer plant_id){
        plantService.deletePlant(farm.getId(),plant_id);
        logger.info("Plant deleted");
        return ResponseEntity.ok(new ApiResponse("Plant deleted"));
    }
    //-------------------------------------   end CRUD  ---------------------------
    //All //Sara
    @GetMapping("/view-plant-of-farm/{farm_name}")
    public ResponseEntity ViewPlantOfFarm(@PathVariable String farm_name){
        logger.info("get all Plant Of Farm");
        return ResponseEntity.ok(plantService.ViewPlantOfFarm(farm_name));
    }

    //Kholud 20
    @GetMapping("/find-plant-by-name/{name}")
    public ResponseEntity findPlantByName(@PathVariable String name) {
        logger.info("find Plant By Name");
        return ResponseEntity.ok(plantService.findPlantByName(name));
    }
//All //sara
    @GetMapping("/find-plant-by-type/{type}")
    public ResponseEntity findPlantByType(@PathVariable String type) {
        logger.info("find Plant By Type");
        return ResponseEntity.ok(plantService.findPlantByType(type));
    }
}
