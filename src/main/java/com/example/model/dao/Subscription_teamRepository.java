package com.example.model.dao;

import com.example.model.entity.Subscription_team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface Subscription_teamRepository extends JpaRepository<Subscription_team,Integer> {

    @Query("select c from Subscription_team c where c.id=?1")
    public Subscription_team findById(Long id);

}
