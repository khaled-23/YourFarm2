package com.example.yourfarm.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class OrderPlant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

   // @Pattern(regexp = "YYYY/MM/DD")
    private LocalDateTime receivedDateAndTime;

 //   @Pattern(regexp = "YYYY/MM/DD")
    private LocalDate dateOfOrder;

    @Pattern(regexp ="^(Delivered|Ready to deliver|waiting|accepted|Rejected|Canceled)$")
    private String status;

    @Positive
    @NotNull(message ="totalPrice must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer totalPrice=0;

    @NotEmpty(message = "Region must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    private String Region;

    @NotEmpty(message = "National address must not be empty")
    @Column(columnDefinition = "varchar(8) not null ")
    private String nationalAddress;

    @Pattern(regexp = "05\\d{8}")
    @Column(columnDefinition = "varchar(20) not null")
    private String phoneNumber;


    @NotEmpty(message = "comment must not be empty")
    @Column(columnDefinition = "varchar(50) not null ")
    private String comment;

    @Positive
    @Max(5)
    @Min(1)
    @Column(columnDefinition = "int")
    private Double evaluation;
    //-------------------------------------------
    @OneToMany(cascade = CascadeType.DETACH,mappedBy = "orderPlant")
    private List<OrderItem> orderItems;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    private Company company;

    @ManyToOne
    @JsonIgnore
    private Farm farm;



}
