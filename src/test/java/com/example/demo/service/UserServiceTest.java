package com.example.demo.service;

import com.example.demo.exceptions.CustomException;
import com.example.demo.model.db.entity.User;
import com.example.demo.model.db.repository.UserRepository;
import com.example.demo.model.dto.request.UserInfoRequest;
import com.example.demo.model.dto.response.UserInfoResponse;
import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.UserStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @InjectMocks
    private UserService userService;
    @Mock
    private UserRepository userRepository;
    @Spy
    private ObjectMapper mapper;

    @Test
    public void createUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");

        User user = new User();
        user.setId(1L);

        when(userRepository.save(any(User.class))).thenReturn(user);

        UserInfoResponse result = userService.createUser(request);
        assertEquals(user.getId(), result.getId());
    }

    @Test(expected = CustomException.class)
    public void createUser_badEmail() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@.test.com");

        userService.createUser(request);
    }

    @Test(expected = CustomException.class)
    public void createUser_userExists() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");

        User user = new User();
        user.setId(1L);

        when(userRepository.findByEmailIgnoreCase(anyString())).thenReturn(Optional.of(user));
        userService.createUser(request);
    }

    @Test
    public void getUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        UserInfoResponse result = userService.getUser(1L);
        assertEquals(user.getId(), result.getId());

    }

    @Test
    public void getUserFromDB() {//проверка в рамках других тестов
    }

    @Test
    public void controlStatus() {
    }

    @Test
    public void updateUser() {
        UserInfoRequest request = new UserInfoRequest();
        request.setEmail("test@test.com");
        request.setGender(Gender.MALE);

        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.updateUser(1L, request);
        verify(userRepository,times(1)).save(any(User.class));
        assertEquals(UserStatus.UPDATED, user.getStatus());
        assertEquals(Gender.MALE, user.getGender());

    }

    @Test
    public void deleteUser() {
        User user = new User();
        user.setId(1L);
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        userService.deleteUser(user.getId());
        verify(userRepository, times(1)).save(any(User.class));
        assertEquals(UserStatus.DELETED,user.getStatus());
    }

    @Test
    public void getAllUsers() {
        User user = new User();
        user.setId(1L);
        user.setAge(25);
        user.setGender(Gender.MALE);

        User user1 = new User();
        user1.setId(2L);
        user1.setAge(20);
        user1.setGender(Gender.MALE);

        User user2 = new User();
        user2.setId(3L);
        user2.setAge(30);
        user2.setGender(Gender.FEMALE);

        List<User> users = List.of(user, user1, user2);

        when(userRepository.findAll()).thenReturn(users);
        Page<UserInfoResponse> result = userService.getAllUsers(1,3,"age", Sort.Direction.ASC,"FEMALE");
        assertEquals(result,users);



    }

}