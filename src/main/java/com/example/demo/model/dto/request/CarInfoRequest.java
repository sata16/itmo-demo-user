package com.example.demo.model.dto.request;

import com.example.demo.model.enums.Color;
import lombok.*;
import lombok.experimental.FieldDefaults;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CarInfoRequest {
    String brand;
    String model;
    Integer seatPlace;
    Double engineCapacity;
    Color color;
    Boolean isNew;
    int doorsCount;
    Long price;
}