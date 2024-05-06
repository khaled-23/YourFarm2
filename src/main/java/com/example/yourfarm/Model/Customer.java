package com.example.yourfarm.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
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
public class Customer {

    @Id
    private Integer id;

    @NotEmpty(message = "Region must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String region;

    @NotEmpty(message = "National address must not be empty")
    @Column(columnDefinition = "varchar(8) not null ")
    private String nationalAddress;

    //------------------------------------------
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<OrderFarmer> orderFarmers;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<OrderGuidance> orderGuidances;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private Set<OrderPlant> orderPlants;

    @OneToOne
    @MapsId
    private User user;
}
