package com.example.yourfarm.DTO;

import com.example.yourfarm.Model.OrderItem;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PlantDTO {

    List<OrderItem> orderItems;

   // @Pattern(regexp = "YYYY/MM/DD")
    private LocalDateTime receivedDateAndTime;

    @NotEmpty(message = "Region must not be empty")
    private String region;

    @NotEmpty(message = "National address must not be empty")
    private String nationalAddress;

    @Pattern(regexp = "05\\d{8}")
    private String phoneNumber;


}
