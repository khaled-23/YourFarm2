package com.example.yourfarm.Model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer Id;

    @NotEmpty(message ="couponNumber must not be empty" )
    @Column(columnDefinition = "varchar(10) not null")
    private StringBuilder couponNumber;

    @Pattern(regexp ="^(valid|expired)$")
    private String status;

    private Integer value;
}
