package com.example.yourfarm.Service;

import com.example.yourfarm.API.ApiException;
import com.example.yourfarm.Model.User;
import com.example.yourfarm.Repository.AuthRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final AuthRepository authRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

       User user = authRepository.findUserByUsername(username);

        if (user == null) {
            throw new ApiException("username or password invalid");
        }
        return user;
    }
}