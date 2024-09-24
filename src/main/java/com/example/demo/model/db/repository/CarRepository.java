package com.example.demo.model.db.repository;

import com.example.demo.model.db.entity.Car;
import com.example.demo.model.enums.CarStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {
    @Query("select c from Car c where c.status <> :status")
    Page<Car> findByStatusNot(Pageable request, CarStatus status);

    @Query("select c from Car c where c.status <> :status and (lower(c.brand) like %:filter%  or  lower(c.model) like %:filter% )")
    Page<Car> findAllByStatusNotFiltered(Pageable request, CarStatus status, @Param("filter") String filter);

    @Query("select c from Car c where c.status <> :status and c.user.id = :id and c.user.id is not null")
    List<Car> findAllCarToUser(@Param("id") Long id,CarStatus status);

}
