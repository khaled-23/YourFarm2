package com.example.yourfarm.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotEmpty(message = "It must not be empty")
    @Size(min = 5,message ="You must enter at least 5 characters" )
    @Column(columnDefinition = "varchar(20) not null unique")
    private String username;

    @NotEmpty(message ="It must not be empty" )
    @Column(columnDefinition = " varchar(255) not null")
    private String password;

    @Pattern(regexp ="^(CUSTOMER|COMPANY|ADMIN|SPECIALIST|FARM|FARMER)$")
    private String role;

    @NotEmpty(message = "name must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String name ;

    @NotEmpty(message ="It must not be empty" )
    @Email
    @Column(columnDefinition = " varchar(40) not null unique ")
    private String email;

    @Pattern(regexp = "05\\d{8}")
    @Column(columnDefinition = "varchar(20) not null")
    private String phoneNumber;

    private String image;


 //----------------------------------------------------



    @OneToOne(cascade = CascadeType.REMOVE,mappedBy = "user", orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Customer customer;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user", orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Company company;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user", orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Specialist specialist;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user", orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Farmer farmer;

    @OneToOne(cascade = CascadeType.ALL,mappedBy = "user", orphanRemoval = true)
    @PrimaryKeyJoinColumn
    @JsonIgnore
    private Farm farm;




//-----------------------------------------------------


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

//    @Override
//    public String getUsername() {
//        return "";
//    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

