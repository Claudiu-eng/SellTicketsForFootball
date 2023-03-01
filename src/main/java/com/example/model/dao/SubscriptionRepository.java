package com.example.model.dao;

import com.example.model.entity.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SubscriptionRepository extends JpaRepository<Subscription,Integer> {

    @Query("select c from Subscription c where c.teamID=?1 and c.stadiumID=?2")
    public List<Subscription> findAllByTeamIDAndStadiumID(Long id,Long id2);

    @Query("select c from Subscription c where c.id=?1 and c.seatID=?2")
    public Subscription findByIdAndSeatID(Long id, Integer number);

}
