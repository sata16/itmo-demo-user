package com.example.demo.service;

import com.example.demo.model.db.entity.Car;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.CarRepository;
import com.example.demo.model.dto.request.CarInfoRequest;
import com.example.demo.model.dto.request.CarToUserRequest;
import com.example.demo.model.dto.response.CarInfoResponse;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.CarStatus;
import com.example.demo.model.enums.Color;
import com.example.demo.model.enums.UserStatus;
import com.example.demo.utils.PaginationUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CarServiceTest {
    @InjectMocks
    private CarService carService;

    @Mock
    private UserService userService;

    @Mock
    private CarRepository carRepository;

    @Spy
    private ObjectMapper mapper;

    @Test
    public void createCar() {
        CarInfoRequest request = new CarInfoRequest();
        Car car = new Car();
        car.setId(1L);
        when(carRepository.save(any(Car.class))).thenReturn(car);
        CarInfoResponse result = carService.createCar(request);
        assertEquals(car.getId(), result.getId());

    }

    @Test
    public void getCar() {
        Car car = new Car();
        car.setId(1L);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        CarInfoResponse result = carService.getCar(car.getId());
        assertEquals(car.getId(), result.getId());
    }

    @Test
    public void updateCar() {
        CarInfoRequest request = new CarInfoRequest();
        request.setBrand("BMW");
        request.setModel("X5");
        request.setColor(Color.BLACK);

        Car car = new Car();
        car.setId(1L);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        carService.updateCar(car.getId(), request);
        verify(carRepository,times(1)).save(any(Car.class));
        assertEquals(CarStatus.UPDATED, car.getStatus());
        assertEquals(request.getBrand(), car.getBrand());
        assertEquals(request.getModel(), car.getModel());
        assertEquals(Color.BLACK, car.getColor());
    }

    @Test
    public void deleteCar() {
        Car car = new Car();
        car.setId(1L);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        carService.deleteCar(car.getId());
        verify(carRepository,times(1)).save(car);
        assertEquals(CarStatus.DELETED, car.getStatus());

    }

    @Test
    public void getAllCars() {
        Pageable pageRequest = PaginationUtil.getPageRequests(1, 10, "brand", Sort.Direction.DESC);

        List<Car> cars = new ArrayList<>();

        cars.add(new Car());
        cars.add(new Car());

        PageImpl<Car> page = new PageImpl<>(cars, pageRequest, cars.size());

        when(carRepository.findAllByStatusNotFiltered(pageRequest, CarStatus.DELETED, "bm".toLowerCase())).thenReturn(page);

        Page<CarInfoResponse> allCars = carService.getAllCars(1, 10, "brand", Sort.Direction.DESC, "bm");

        assertEquals(cars.size(), allCars.getTotalElements());

        CarInfoResponse carInfoResponse = allCars.getContent().get(0);

        assertNotNull(carInfoResponse);
    }

    @Test
    public void addCarToUser() {
        Car car = new Car();
        car.setId(1L);
        when(carRepository.findById(car.getId())).thenReturn(Optional.of(car));
        User user = new User();
        user.setId(1L);
        user.setCars(new ArrayList<>());
        when(userService.getUserFromDB(user.getId())).thenReturn(user);
        when(userService.updateUserData(any(User.class))).thenReturn(user);
        CarToUserRequest request = CarToUserRequest.builder()
                .carId(car.getId())
                .userId(user.getId())
                .build();

        carService.addCarToUser(request);
        verify(carRepository,times(1)).save(any(Car.class));
        assertEquals(user.getId(), car.getUser().getId());

    }

    @Test
    public void getAllCarToUser() {
        Car car = new Car();
        car.setId(1L);
        Car car2 = new Car();
        car2.setId(2L);
        List<Car> cars = List.of(car, car2);
        User user = new User();
        user.setId(1L);
        user.setCars(cars);
        when(userService.getUserFromDB(user.getId())).thenReturn(user);
        when(carRepository.findAllCarToUser(user.getId(),CarStatus.DELETED)).thenReturn(cars);
        List<CarInfoResponse> result = carService.getAllCarToUser(user.getId());
        assertEquals(cars.size(), result.size());


    }


}