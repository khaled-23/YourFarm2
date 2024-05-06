package com.example.yourfarm;//package com.example.yourfarm;
//
//import com.example.yourfarm.Model.*;
//import com.example.yourfarm.Repository.AuthRepository;
//import com.example.yourfarm.Repository.FarmRepository;
//import com.example.yourfarm.Repository.PlantRepository;
//import com.example.yourfarm.Service.PlantService;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import static org.mockito.Mockito.*;
//import static org.mockito.Mockito.times;
//
//@ExtendWith(MockitoExtension.class)
//public class PlantServiceTest {
//
//    @InjectMocks
//    PlantService plantService;
//
//    @Mock
//    PlantRepository plantRepository;
//
//    @Mock
//    AuthRepository authRepository;
//    @Mock
//    FarmRepository farmRepository;
//
//   User user;
//   Farm farm;
//
//    Plant plant1,plant2,plant3;
//
//    List<Plant> plants;
//
//    @BeforeEach
//    void setUp() {
//        user = new User(1,"sara","123","CUSTOMER","SARA","sara@gmail.com","0512345678","esdfyguhio",null,null,null,null,null);
//        farm = new Farm(1,"S","qwertyui","sdfghjk",12345,0.0,0,0,null,null,null,user);
//
//        plant1= new Plant(1," plant1","QWERT",10,"Indoor plants",1,farm);
//        plant2= new Plant(2," plant2","GHJK",20,"Indoor plants",2,farm);
//        plant3= new Plant(3," plant3","GHJK",30,"Indoor plants",3,farm);
//        plants = new ArrayList<Plant>();
//        plants.add(plant1);
//        plants.add(plant2);
//        plants.add(plant3);
//    }
//
//
//    @Test
//    public void testGetAllPlant() {
//        when(this.plantRepository.findAll()).thenReturn(plants);
//        when(this.plantRepository.findAll().isEmpty()).thenReturn(false);
//        List<Plant> plantList = plantService.getAllPlant();
//        Assertions.assertEquals(3, plantList.size());
//        verify(plantRepository,times(3)).findPlantByFarm(farm);
//        verify(plantRepository,times(3)).findAll().isEmpty();
//    }
//
//@Test
//public void addPlantTest() {
//    // تحضير البيانات المزيفة
//    Farm farm = new Farm();
//    farm.setId(1);
//
//    Plant plant = new Plant();
//    plant.setId(1);
//
//    when(farmRepository.findFarmById(1)).thenReturn(farm);
//
//    // استدعاء الميثود التي تحتاج لاختبارها
//    plantService.addPlant(1, plant);
//
//    // التحقق من أن المزرعة تم تحميلها وتعيينها بشكل صحيح للنبات
//    verify(farmRepository, times(1)).findFarmById(1);
//    Assertions.assertEquals(farm, plant.getFarm());
//
//    // التحقق من أن الميثود save() تم استدعاؤها للنبات بشكل صحيح
//    verify(plantRepository, times(1)).save(plant);
//}
//
//    @Test
//    public void updateTodoTest() {
//        when(this.authRepository.findUserById(user.getId())).thenReturn(user);
//        when(this.toDoRepository.findToDoById(toDo1.getId())).thenReturn(toDo1);
//
//        toDoService.updateToDo(user.getId() ,toDo1.getId(),toDo2);
//
//        verify(authRepository,times(1)).findUserById(user.getId());
//        verify(toDoRepository,times(1)).findToDoById(toDo1.getId());
//        verify(toDoRepository,times(1)).save(toDo2);
//    }
//
//    @Test
//    public void deleteTodoTest() {
//        when(this.toDoRepository.findToDoById(toDo1.getId())).thenReturn(toDo1);
//        when(this.authRepository.findUserById(user.getId())).thenReturn(user);
//        toDoService.deleteToDo(user.getId(),toDo1.getId());
//
//        verify(authRepository,times(1)).findUserById(user.getId());
//        verify(toDoRepository,times(1)).delete(toDo1);
//    }
//
//
//}
