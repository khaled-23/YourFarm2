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
public class Farm {
    @Id
    private Integer id;

    @NotEmpty(message = "name must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String name;

    @NotEmpty(message = "Region must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String region;

    @NotEmpty(message ="commercial Register must not be empty" )
    @Column(columnDefinition = "varchar(15) not null unique")
    private String commercialRegister;

    @Positive
    @Column(columnDefinition = "int")
    private Integer area;

    @Positive
    @Max(5)
    @Min(1)
    @Column(columnDefinition = "int")
    private Double evaluation;


    @Positive
    @Column(columnDefinition = "int")
    private Integer numberOfEvaluation;

    private Integer sales=0;


    //------------------------------------------

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farm")
    private Set<OrderPlant> orderPlants;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farm")
    private Set<Contract> contracts;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "farm")
    private Set<Plant> plants;

    @OneToOne
    @MapsId
    private User user;
}
