package com.example.demo.service;

import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;


import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    public UserInfoResponse createUser(UserInfoRequest request) {
        if(!EmailValidator.getInstance().isValid(request.getEmail())) {
            return  null;
        }

        return UserInfoResponse.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .age(request.getAge())
                .gender(request.getGender())
                .build();
    }

    public UserInfoResponse getUser(Long id) {
        return null;
    }

    public UserInfoResponse updateUser(Long id, UserInfoRequest request) {
        if(!EmailValidator.getInstance().isValid(request.getEmail())) {
            return  null;
        }

        return UserInfoResponse.builder()
                .email(request.getEmail())
                .password(request.getPassword())
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .middleName(request.getMiddleName())
                .age(request.getAge())
                .gender(request.getGender())
                .build();
    }

    public void deleteUser(Long id) {

    }

    public List<UserInfoResponse> getAllUsers() {
        return Collections.emptyList();
    }
}
