package com.example.demo.service;

import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.Car;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.CarRepository;
import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.request.CarToUserRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.model.enums.CarStatus;
import com.example.demo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CarService {
    private final UserService userService;
    private  final ObjectMapper mapper;
    private final CarRepository carRepository;

    public CarInfoResponse createCar(CarInfoRequest request) {

        Car car = mapper.convertValue(request, Car.class);
        car.setCreatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.CREATED);

        Car save = carRepository.save(car);

        return mapper.convertValue(save, CarInfoResponse.class);
    }

    private Car getCarFromDB(Long id){

        return carRepository.findById(id).orElseThrow(()->new CustomException("Car not found", HttpStatus.NOT_FOUND));
    }

    public CarInfoResponse getCar(Long id) {
        Car car = getCarFromDB(id);

        return mapper.convertValue(car, CarInfoResponse.class);
    }

    public CarInfoResponse updateCar(Long id, CarInfoRequest request) {
        Car car = getCarFromDB(id);

        car.setBrand(request.getBrand() == null ? car.getBrand() : request.getBrand());
        car.setModel(request.getModel() == null ? car.getModel() : request.getModel());
        car.setSeatPlace(request.getSeatPlace() == null ? car.getSeatPlace() : request.getSeatPlace());
        car.setEngineCapacity(request.getEngineCapacity() == null ? car.getEngineCapacity() : request.getEngineCapacity());
        car.setColor(request.getColor() == null ? car.getColor() : request.getColor());
        car.setIsNew(request.getIsNew() == null ? car.getIsNew() : request.getIsNew());
        car.setDoorsCount(request.getDoorsCount() == 0 ? car.getDoorsCount() : request.getDoorsCount());
        car.setPrice(request.getPrice() == null ? car.getPrice() : request.getPrice());

        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.UPDATED);

        Car save = carRepository.save(car);
        return mapper.convertValue(save, CarInfoResponse.class);
    }

    public void deleteCar(Long id) {
        Car car = getCarFromDB(id);
        car.setUpdatedAt(LocalDateTime.now());
        car.setStatus(CarStatus.DELETED);
        carRepository.save(car);
    }

    public Page<CarInfoResponse> getAllCars(Integer page, Integer perPage, String sort,
                                            Sort.Direction order, String filter) {
        Pageable pageRequest = PaginationUtil.getPageRequests(page,perPage, sort,order);
        Page<Car> all;
        if(filter == null){
            all = carRepository.findByStatusNot(pageRequest, CarStatus.DELETED);
        }else{
            all = carRepository.findAllByStatusNotFiltered(pageRequest,CarStatus.DELETED,filter.toLowerCase());
        }
        List<CarInfoResponse> content = all.getContent().stream()
                .map(car -> mapper.convertValue(car, CarInfoResponse.class))
                .collect(Collectors.toList());

        return new PageImpl<>(content,pageRequest,all.getTotalElements());
    }


    public void addCarToUser(CarToUserRequest request) {
        Car car = carRepository.findById(request.getCarId())
                .orElseThrow(()-> new CustomException("Car not found", HttpStatus.NOT_FOUND));

        User userFromDB = userService.getUserFromDB(request.getUserId());

        userFromDB.getCars().add(car);
        userService.updateUserData(userFromDB);

        car.setUser(userFromDB);
        carRepository.save(car);

    }

    public List<CarInfoResponse> getAllCarToUser(Long id) {
        //Проверка наличия пользователя
        User userFromDB = userService.getUserFromDB(id);
        //Проверка статуса пользователя
        userService.controlStatus(id);

        return carRepository.findAllCarToUser(id,CarStatus.DELETED).stream()
                .map(car->mapper.convertValue(car,CarInfoResponse.class))
                .collect(Collectors.toList());



    }

}
