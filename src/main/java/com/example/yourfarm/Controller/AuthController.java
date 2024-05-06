package com.example.yourfarm.Controller;

import com.example.yourfarm.API.ApiResponse;
import com.example.yourfarm.Service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
//import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;
import com.example.yourfarm.Model.User;

import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    Logger logger = LoggerFactory.getLogger(AuthController.class);

//ADMIN
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUser(){
        logger.info("all user requested");
        return ResponseEntity.ok(authService.getAllUser());
    }
//ALL
    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid User user){
        authService.register(user);
        logger.info("guest register");
        return ResponseEntity.ok(new ApiResponse("user added"));
    }

//Company - customer -
    @PutMapping("/update")
    public ResponseEntity updateUser(@AuthenticationPrincipal User user, @RequestBody User updatedUser){
        authService.updateUser(user.getId(), updatedUser);
        logger.info("user updated");
        return ResponseEntity.ok(new ApiResponse("user updated"));
    }


    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity deleteUser(@PathVariable Integer user_id){
        authService.deleteUser(user_id);
        logger.info("user deleted");
        return ResponseEntity.ok(new ApiResponse("User deleted"));
    }

    @PostMapping("/login")
    public ResponseEntity  login( String username, String password){

        return ResponseEntity.ok().body(" ");
    }

    @PostMapping("/logOut")
    public ResponseEntity  logOut(){
        return ResponseEntity.ok().body(" ");
    }




}
