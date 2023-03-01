package com.example.model.dao;

import com.example.model.entity.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match,Integer> {

    public Match findById(Long Id);

    @Query("select c from Match c where c.homeTeamID=?1 or c.awayTeamID=?1")
    public List<Match> findAllByHomeTeamIDOrAwayTeamID(Integer id);

}
