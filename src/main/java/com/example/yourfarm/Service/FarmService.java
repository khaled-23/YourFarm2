package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.DTO.CustomerDTO;
import com.example.yourfarm.DTO.FarmDTO;
import com.example.yourfarm.DTO.FarmerDTO;
import com.example.yourfarm.Model.*;
import com.example.yourfarm.Repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FarmService {


    private final FarmRepository farmRepository;
    private final AuthRepository authRepository;
    private final AuthService authService;
    private final OrderPlantRepository orderPlantRepository;
    private final ContractRepository contractRepository;


    //ALL
    public List<Farm> getAllFarm(){
        if (farmRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return farmRepository.findAll();
    }

    //FARM
    public void register(FarmDTO farm){
        User user =new User(null,farm.getUsername(),farm.getPassword(),"FARM",farm.getName(),farm.getEmail(),farm.getPhoneNumber(),farm.getImage(),null,null,null,null,null);

        authService.register(user);

        Farm farm1= new Farm(user.getId(),farm.getName(), farm.getRegion(), farm.getCommercialRegister(), farm.getArea(),0.0,0,0,null,null,null,user);
        farmRepository.save(farm1);

    }

    //FARM
    public void update(Integer farmId, FarmDTO farm) {
        User user = authRepository.findUserById(farmId);
        Farm farm1 = farmRepository.findFarmById(farmId);

        String hash = new BCryptPasswordEncoder().encode(farm.getPassword());

        user.setName(farm.getName());
        user.setEmail(farm.getEmail());
        user.setPhoneNumber(farm.getPhoneNumber());
        user.setPassword(hash);
        user.setUsername(farm.getUsername());
        authRepository.save(user);

        farm1.setUser(user);
        farmRepository.save(farm1);
    }

    //ADMIN
    public void deleteFarm(Integer farmId) {
        Farm farm1 = farmRepository.findFarmById(farmId);
        if (farm1 == null) {
            throw new ApiException(" Farm not found");
        }
        farmRepository.delete(farm1);
    }

    //-------------------------------- End CRUD ---------------------------------


    //khaled
    //all roles
    public List<HashMap<String,String>> viewCommentFarm(Integer farmId){
        List<HashMap<String,String>> comments = new ArrayList<>();
        String name;
        String comment;
        for(OrderPlant orderPlant : orderPlantRepository.findOrderPlants()){
            HashMap<String,String> commentHash = new HashMap<>();
            if(orderPlant.getComment() != null && orderPlant.getFarm().getId() == farmId) {
                if(orderPlant.getCustomer() != null) {
                    name = "name: " + orderPlant.getCustomer().getUser().getName();
                    comment = "comment: " + orderPlant.getComment();
                    commentHash.put(name,comment);
                    comments.add(commentHash);
                } else if (orderPlant.getCompany() != null) {
                    name = "name: " + orderPlant.getCompany().getUser().getName();
                    comment = "comment: " + orderPlant.getComment();
                    commentHash.put(name,comment);
                    comments.add(commentHash);
                }
            }
        }
        return comments;
    }
//sara
    public List<Farm> bestEvaluation() {
        ;List<Farm> farms = farmRepository.searchTopByEvaluation();

        return farms;
    }
//sara
    public List<Farm> bestSales() {
        ;List<Farm> farms = farmRepository.searchTopBySales();

        return farms;
    }

}
