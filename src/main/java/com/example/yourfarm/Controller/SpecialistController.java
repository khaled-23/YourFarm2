package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.DTO.SpecialistDTO;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.Plant;
import com.example.yourfarm.Model.Specialist;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Service.SpecialistService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/specialist")
@RequiredArgsConstructor
public class SpecialistController {
    private final SpecialistService specialistService;
    Logger logger = LoggerFactory.getLogger(SpecialistController.class);

    //All
    @GetMapping("/get")
    public ResponseEntity getAllSpecialist(){
        logger.info("get all Specialist");
        return ResponseEntity.ok(specialistService.getAllSpecialist());
    }

//All
    @PostMapping("/add")
    public ResponseEntity registerSpecialist( @RequestBody @Valid SpecialistDTO specialist){
       specialistService.register(specialist);
        return ResponseEntity.ok(new ApiResponse(" Specialist added"));
    }

//SPECIALIST
    @PutMapping("/update/{specialistId}")
    public ResponseEntity updateSpecialist(@AuthenticationPrincipal Specialist specialist, @RequestBody @Valid SpecialistDTO specialist1 ){
        specialistService.update(specialist.getId(),specialist1);
        logger.info("Specialist updated");
        return ResponseEntity.ok(new ApiResponse("Specialist updated"));
    }
//ADMIN
    @DeleteMapping("/delete/{specialistId}")
    public ResponseEntity deleteSpecialist(@AuthenticationPrincipal User user, @PathVariable Integer specialistId){
        specialistService.deleteSpecialist(specialistId);
        logger.info("Specialist deleted");
        return ResponseEntity.ok(new ApiResponse("Specialist deleted"));
    }

    //-------------------------------------   end CRUD  ---------------------------


    //all //khaled
    @GetMapping("/comments/{specialist_id}")
    public ResponseEntity viewComments(@PathVariable Integer specialist_id){
        logger.info("farm comments");
        return ResponseEntity.ok(specialistService.viewCommentSpecialist(specialist_id));
    }

    //All //sara
    @GetMapping("/getBestEvaluation")
    public ResponseEntity getBestEvaluationForSpecialist(){
        logger.info("get bestEvaluationForSpecialist");
        return ResponseEntity.ok(specialistService.bestEvaluation());
    }
}
