package com.example.model.dao;

import com.example.model.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TeamRepository extends JpaRepository<Team,Integer> {

    @Query("select c from Team c where c.id=?1")
    public Team findById(Long id);

    @Query("select c from Team c where c.championshipID=?1")
    public List<Team> findAllByChampionshipID(Integer id);
}
