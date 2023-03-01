package com.example.model.dao;

import com.example.model.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {

    @Query("select c from Ticket c where c.matchID=?1")
    public List<Ticket> findAllByMatchID(Long id);

}
