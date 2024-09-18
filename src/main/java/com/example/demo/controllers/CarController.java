package com.example.demo.controllers;

import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Tag(name = "Машины")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cars")
public class CarController {
    private final CarService carService;
    //создание
    @PostMapping
    @Operation(summary = "Создать машину")
    public CarInfoResponse createCar(@RequestBody CarInfoRequest request) {
        return carService.createCar(request);
    }
    //получение
    @GetMapping("/{id}")
    @Operation(summary = "Получить машину по ID")
    public CarInfoResponse getCar(@PathVariable Long id){
        return carService.getCar(id);
    }
    //обновить информацию
    @PutMapping("/{id}")
    @Operation(summary = "Обновить машину по ID")
    public CarInfoResponse updateCar(@PathVariable Long id, @RequestBody CarInfoRequest request) {
        return carService.updateCar(id,request);
    }
    //удаление
    @DeleteMapping("/{id}")
    @Operation(summary = "Удалить машину по ID")
    public void deleteCar(@PathVariable Long id){
        carService.deleteCar(id);
    }
    //получение всех cars
    @GetMapping("/all")
    @Operation(summary = "Получить список машин")
    public List<CarInfoResponse> getAllCars(){
        return carService.getAllCars();
    }
}
