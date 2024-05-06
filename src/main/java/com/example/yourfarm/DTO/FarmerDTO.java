package com.example.yourfarm.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmerDTO {

    private Integer Id;

    @NotEmpty(message = "It must not be empty")
    @Size(min = 5,message ="You must enter at least 5 characters" )
    private String username;

    @NotEmpty(message ="It must not be empty" )
    private String password;

    @NotEmpty(message = "name must not be empty")
    private String name ;

    @NotEmpty(message ="It must not be empty" )
    @Email
    private String email;

    @Pattern(regexp = "05\\d{8}")
    private String phoneNumber;

    private String image;

    @NotNull(message ="Years Of Experience must not be empty" )
    private Integer yearsOfExperience;

    @NotEmpty(message = "Licenses must not be empty")
    private String licenses;

    @NotNull(message ="price must not be empty" )
    private Integer price;

    @NotEmpty(message = "Region must not be empty")
    private String region;


}
