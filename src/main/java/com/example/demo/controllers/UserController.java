package com.example.demo.controllers;

import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

//    @GetMapping
//    public String sayHello(){
//        return "Hello World";
//    }
    @PostMapping
    public UserInfoResponse createUser(@RequestBody UserInfoRequest request) {
        return new UserInfoResponse();
    }
    @GetMapping("/{id}")
    public UserInfoResponse getUser(@PathVariable Long id) {
        return new UserInfoResponse();
    }
    @PutMapping("/{id}")
    public UserInfoResponse updateUser(@PathVariable Long id, @RequestBody UserInfoRequest request) {
        return new UserInfoResponse();
    }
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {

    }
    @GetMapping("/all")
    public List<UserInfoResponse> getAllUsers() {
        return Collections.singletonList(new UserInfoResponse());
    }

}
