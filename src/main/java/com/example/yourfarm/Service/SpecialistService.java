package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.DTO.SpecialistDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.AuthRepository;
import com.example.yourfarm.Repository.CustomerRepository;
import com.example.yourfarm.Repository.OrderGuidanceRepository;
import com.example.yourfarm.Repository.SpecialistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SpecialistService {


    private final SpecialistRepository specialistRepository;
    private final AuthRepository authRepository;
    private final AuthService authService;
    private final OrderGuidanceRepository orderGuidanceRepository;


    //ALL
    public List<Specialist> getAllSpecialist(){
        if (specialistRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return specialistRepository.findAll();
    }

    //SPECIALIST
    public void register(SpecialistDTO specialist){
        User user =new User(null,specialist.getUsername(),specialist.getPassword(),"SPECIALIST",specialist.getName(),specialist.getEmail(),specialist.getPhoneNumber(),specialist.getImage(),null,null,null,null,null);
        authService.register(user);

        Specialist specialist1= new Specialist(user.getId(),specialist.getYearsOfExperience(),specialist.getLicenses(),specialist.getPricePresence(),specialist.getPriceOnline(),specialist.getRegion(),0.0,0,null,user);
        specialistRepository.save(specialist1);

    }

    //SPECIALIST
    public void update(Integer specialistId, SpecialistDTO specialist) {
        User user = authRepository.findUserById(specialistId);
        Specialist specialist1 = specialistRepository.findSpecialistById(specialistId);

        String hash = new BCryptPasswordEncoder().encode(specialist.getPassword());

        user.setName(specialist.getName());
        user.setEmail(specialist.getEmail());
        user.setPhoneNumber(specialist.getPhoneNumber());
        user.setPassword(hash);
        user.setUsername(specialist.getUsername());
        authRepository.save(user);

        specialist1.setUser(user);
        specialistRepository.save(specialist1);
    }

    //ADMIN
    public void deleteSpecialist(Integer specialistId) {
        Specialist specialist1 = specialistRepository.findSpecialistById(specialistId);
        if (specialist1 == null) {
            throw new ApiException("specialist not found");
        }
        specialistRepository.delete(specialist1);
    }

    //-------------------------------- End CRUD ---------------------------------

    //khaled
    //all role
    public List<HashMap<String,String>> viewCommentSpecialist(Integer specialistId){
        List<HashMap<String,String>> comments = new ArrayList<>();
        String name;
        String comment;
        for(OrderGuidance orderGuidance : orderGuidanceRepository.findOrderGuidance()){
            HashMap<String,String> commentHash = new HashMap<>();
            if(orderGuidance.getComment() != null && orderGuidance.getSpecialist().getId() == specialistId) {
                if (orderGuidance.getCustomer() != null) {
                    name = "name: " + orderGuidance.getCustomer().getUser().getName();
                    comment = "comment: " + orderGuidance.getComment();
                    commentHash.put(name,comment);
                    comments.add(commentHash);
                } else if (orderGuidance.getCompany() != null) {
                    name = "name: " + orderGuidance.getCompany().getUser().getName();
                    comment = "comment: " + orderGuidance.getComment();
                    commentHash.put(name,comment);
                    comments.add(commentHash);
                }
            }
        }
        return comments;
    }

    public List<Specialist> bestEvaluation() {
        List<Specialist> specialists = specialistRepository.searchTopByEvaluation();

        return specialists;
    }
}
