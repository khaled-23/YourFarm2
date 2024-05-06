package com.example.yourfarm.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Farmer {
    @Id
    private Integer id;

    @NotNull(message ="Years Of Experience must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer yearsOfExperience;

    @NotEmpty(message = "Licenses must not be empty")
    @Column(columnDefinition = "varchar(100) not null ")
    private String licenses;

    @NotNull(message ="price must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer price;

    @NotEmpty(message = "Region must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String region;


    @Positive
    @Max(5)
    @Min(1)
    @Column(columnDefinition = "int")
    private Double evaluation;

    @Positive
    @Column(columnDefinition = "int")
    private Integer numberOfEvaluation;
    //------------------------------------------

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farmer")
    private Set<OrderFarmer> orderFarmers;

    @OneToOne
    @MapsId
    private User user;
}
