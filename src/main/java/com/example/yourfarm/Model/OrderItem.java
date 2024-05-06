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

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class OrderItem {

    @Id
    @GeneratedValue
    private Integer id;

    private Integer plantId;

    @Positive
    private Integer quantity;

    @NotEmpty(message = "name must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String name ;

    @NotNull(message ="price must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer price;


    //------------------------------


    @ManyToOne
    @JsonIgnore
    private OrderPlant orderPlant;
}
