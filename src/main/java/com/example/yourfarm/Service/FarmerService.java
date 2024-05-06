package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.DTO.FarmerDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.AuthRepository;
import com.example.yourfarm.Repository.CustomerRepository;
import com.example.yourfarm.Repository.FarmerRepository;
import com.example.yourfarm.Repository.OrderFarmerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FarmerService {

    private final FarmerRepository farmerRepository;
    private final AuthRepository authRepository;
    private final AuthService authService;
    private final OrderFarmerRepository orderFarmerRepository;


    //ALL
    public List<Farmer> getAllFarmer(){
        if (farmerRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return farmerRepository.findAll();
    }

    //FARMER
    public void register(FarmerDTO farmer){
        User user =new User(null,farmer.getUsername(),farmer.getPassword(),"FARMER",farmer.getName(),farmer.getEmail(),farmer.getPhoneNumber(), farmer.getImage(), null,null,null,null,null);
        authService.register(user);
        Farmer farmer1= new Farmer(user.getId(),farmer.getYearsOfExperience(),farmer.getLicenses(),farmer.getPrice(), farmer.getRegion(),0.0,0, null,user);
        farmerRepository.save(farmer1);

    }

    //FARMER
    public void update(Integer farmerId, FarmerDTO farmer) {
        User user = authRepository.findUserById(farmerId);
        Farmer farmer1= farmerRepository.findFarmerById(farmerId);

        String hash = new BCryptPasswordEncoder().encode(farmer.getPassword());

        user.setName(farmer.getName());
        user.setEmail(farmer.getEmail());
        user.setPhoneNumber(farmer.getPhoneNumber());
        user.setPassword(hash);
        user.setUsername(farmer.getUsername());
        authRepository.save(user);

        farmer1.setUser(user);
        farmerRepository.save(farmer1);
    }

    //ADMIN
    public void deleteFarmer(Integer farmerId) {
        Farmer farmer1= farmerRepository.findFarmerById(farmerId);
        if (farmer1 == null) {
            throw new ApiException(" Farmer not found");
        }
        farmerRepository.delete(farmer1);
    }
    //-------------------------------- End CRUD ---------------------------------

    //khaled
    //all role
    public List<HashMap<String,String>> viewCommentFarmer(Integer farmerId){
        List<HashMap<String,String>> comments = new ArrayList<>();
        String name;
        String comment;
        for(OrderFarmer orderFarmer : orderFarmerRepository.findOrderFarmer()){
            HashMap<String,String> commentHash = new HashMap<>();
            if(orderFarmer.getComment() != null && orderFarmer.getFarmer().getId() == farmerId) {
                if (orderFarmer.getCustomer() != null) {
                    name = "name: " + orderFarmer.getCustomer().getUser().getName();
                    comment = "comment: " + orderFarmer.getComment();
                    commentHash.put(name,comment);
                    comments.add(commentHash);
                } else if (orderFarmer.getCompany() != null) {
                    name = "name: " + orderFarmer.getCompany().getUser().getName();
                    comment = "comment: " + orderFarmer.getComment();
                    commentHash.put(name,comment);
                    comments.add(commentHash);
                }
            }
        }
        return comments;
    }
    //sara
    public List<Farmer> bestEvaluation() {
        ;List<Farmer> farmers =farmerRepository.searchTopByEvaluation();

        return farmers;
    }

}
