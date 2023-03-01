package com.example.model.dao;

import com.example.model.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StadiumRepository extends JpaRepository<Stadium,Integer> {

    @Query("select c from Stadium c where c.id=?1")
    public Stadium findById(Long id);
}
