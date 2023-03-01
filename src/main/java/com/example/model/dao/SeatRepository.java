package com.example.model.dao;

import com.example.model.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Integer> {

    @Query("select c from Seat c where c.stadium_id=?1")
    public List<Seat> findAllByStadium_id(Long id);

    @Query("select c from Seat c where c.stadium_id=?1 and c.seatNumber=?2")
    public Seat findByStadium_idAndSeatNumber(Long id,Integer number);

    @Query("select c from Seat c where c.id=?1")
    public Seat findById(Long id);

}
