package com.example.demo.controllers;

import com.example.demo.model.dto.request.AllCarToUserRequest;
import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.request.CarToUserRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public Page<CarInfoResponse> getAllCars(@RequestParam (defaultValue = "1")  Integer page,
                                            @RequestParam(defaultValue = "10") Integer perPage,
                                            @RequestParam(defaultValue = "brand") String sort,
                                            @RequestParam(defaultValue = "ASC") Sort.Direction order,
                                            @RequestParam(required = false) String filter){
        return carService.getAllCars(page, perPage, sort, order, filter);
    }
    //добавляем автомобиль пользователю
    @PostMapping("/CarToUser")
    @Operation(summary = "Добавить автомобиль пользователю")
    public void addCarToUser(@RequestBody @Valid CarToUserRequest request) {
        carService.addCarToUser(request);
    }
    //все авто пользователя
    @GetMapping("/AllCarToUser/{id}")
    @Operation(summary = "Получить все машины по ID пользователя")
    public List<CarInfoResponse> getAllCarToUser(@PathVariable Long id) {
        return carService.getAllCarToUser(id);
    }


}
