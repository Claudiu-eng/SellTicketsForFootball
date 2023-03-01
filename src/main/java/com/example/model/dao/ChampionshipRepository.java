package com.example.model.dao;

import com.example.model.entity.Championship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChampionshipRepository extends JpaRepository<Championship,Integer> {

    @Query("select c from Championship c where c.id=?1")
    public Championship findById(Long id);


}
