package com.example.yourfarm.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
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
public class Plant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotEmpty(message = "name must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String name ;

    @NotEmpty(message = "name must not be empty")
    @Column(columnDefinition = "varchar(255) not null ")
    private String description ;

    @NotNull(message ="price must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer price;

    @NotEmpty(message = "type of plant must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    @Pattern(regexp ="^(Indoor plants|Outdoor plants)$")
    private String type;

    @Positive
    @NotNull(message ="Quantity must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer quantity=0;

//----------------------------------------------------------



    @ManyToOne
    @JsonIgnore
    private Farm farm;
}
