package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.Model.Farm;
import com.example.yourfarm.Model.Plant;
import com.example.yourfarm.Repository.FarmRepository;
import com.example.yourfarm.Repository.OrderPlantRepository;
import com.example.yourfarm.Repository.PlantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PlantService {

    private final PlantRepository plantRepository;
    private final FarmRepository farmRepository;
    private final OrderPlantRepository orderPlantRepository;

    //All
    public List<Plant> getAllPlant(){
        if (plantRepository.findAll().isEmpty())
            throw new ApiException("EmptyList");
        else return plantRepository.findAll();
    }

    //farm
    public void addPlant(Integer farmId , Plant plant){
        Farm farm =farmRepository.findFarmById(farmId);
        plant.setFarm(farm);
        plantRepository.save(plant);

    }

    //farm
    public void update(Integer farmId ,Integer plantId,Plant plant) {
        Plant plant1 = plantRepository.findPlantById(plantId);
        if (plant1 == null) {
            throw new ApiException("Plant not found");
        }
        if (plant1.getFarm().getId() != farmId) {
            throw new ApiException("Can not update this plant");
        }

        plant1.setName(plant.getName());
        plant1.setPrice(plant.getPrice());
        plant1.setQuantity(plant.getQuantity());
        plant1.setType(plant.getType());

        plantRepository.save(plant);

    }

    //farm
    public void deletePlant(Integer farmId , Integer plantId) {
        Plant plant1 = plantRepository.findPlantById(plantId);
        if (plant1 == null) {
            throw new ApiException("Plant not found");
        }
        if (plant1.getFarm().getId() != farmId) {
            throw new ApiException("Can not update this plant");
        }
        plantRepository.delete(plant1);
    }

    //-------------------------- end CRUD  ---------------------------

    public Set<Plant> ViewPlantOfFarm(String farmName) {
        Farm farm1 = farmRepository.findFarmByName(farmName);
        if (farm1.getPlants() == null) {
            throw new ApiException(" Plants not found");
        }
        return farm1.getPlants();
    }
    //KHLOUD
    public Plant findPlantByName(String name) {
        Plant plant = plantRepository.findPlantByName(name);

        if (plant == null) {
            throw new ApiException("Plant with name"+name +"not found");
        }
        return plant;
    }

    public  List<Plant> findPlantByType(String type) {
        List<Plant> plant = plantRepository.findPlantByType(type);

        if (plant == null) {
            throw new ApiException("Plants with type"+type +"not found");
        }
        return plant;
    }



}
