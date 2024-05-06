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

@Getter
@Setter
@AllArgsConstructor
@Entity
@NoArgsConstructor
public class OrderGuidance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message ="Duration must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer duration;

    @NotNull(message ="price must not be empty" )
    @Column(columnDefinition = "int not null")
    private Integer totalPrice;

    @NotEmpty(message = "type of Guidance must not be empty")
    @Column(columnDefinition = "varchar(20) not null ")
    @Pattern(regexp ="^(Presence|online)$")
    private String type;

  //  @Pattern(regexp = "YYYY/MM/DD")
    private LocalDateTime guidanceDateAndTime;

    @Pattern(regexp ="^(finished|Rejected|waiting|accepted|Canceled)$")
    private String status;

    @NotEmpty(message = "comment must not be empty")
    @Column(columnDefinition = "varchar(50) not null ")
    private String comment;

    @Positive
    @Max(5)
    @Min(1)
    @Column(columnDefinition = "int")
    private Double evaluation;

//-----------------------------------
    @ManyToOne
    @JsonIgnore
    private Specialist specialist;

    @ManyToOne
    @JsonIgnore
    private Customer customer;

    @ManyToOne
    @JsonIgnore
    private Company company;



}
