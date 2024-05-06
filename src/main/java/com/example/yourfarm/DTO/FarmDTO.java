package com.example.yourfarm.DTO;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FarmDTO {

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

    @NotEmpty(message = "Region must not be empty")
    private String region;



    @NotEmpty(message ="commercial Register must not be empty" )
    private String commercialRegister;

    @Positive
    private Integer area;

}
