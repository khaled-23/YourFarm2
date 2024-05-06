package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.Model.Customer;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Repository.AuthRepository;
import com.example.yourfarm.Repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthRepository authRepository;

    //ADMIN
    public List<User> getAllUser() {
        return authRepository.findAll();
    }

    //CUSTOMER - FARM -FARMER - COMPANY - SPECIALIST
    public void register(User user) {
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());
        user.setPassword(hash);
        authRepository.save(user);

    }

    //CUSTOMER - FARM -FARMER - COMPANY - SPECIALIST
    public void updateUser( Integer userId , User user) {

        User user1 = authRepository.findUserById(userId);
        String hash = new BCryptPasswordEncoder().encode(user.getPassword());

        user1.setPassword(hash);
        user1.setEmail(user.getEmail());
        user1.setName(user.getName());
        user1.setUsername(user.getUsername());
      user1.setPhoneNumber(user.getPhoneNumber());
      user1.setImage(user.getImage());

        authRepository.save(user1);
    }

    //ADMIN
    public void deleteUser(Integer userId) {
        User user = authRepository.findUserById(userId);
        if (user == null) {
            throw new ApiException("not found");
        }
        authRepository.delete(user);
    }
//-------------------------------------   end CRUD  ---------------------------

}
