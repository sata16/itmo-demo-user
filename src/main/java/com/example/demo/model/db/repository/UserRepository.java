package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.User;
import com.example.demo.model.enums.Gender;
import com.example.demo.model.enums.UserStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select us from User us where us.status <> :status")
    Page<User> findByUserStatusNot(Pageable request, UserStatus status);
    //фильтр на возраст
    @Query("select us from User us where us.status <> :status and cast(us.gender AS string)like %:filter% ")
    Page<User> findByUserStatusAndGenderNot(Pageable request, UserStatus status, @Param("filter") String filter);

    Optional<User> findByEmailIgnoreCase(String email);

}
