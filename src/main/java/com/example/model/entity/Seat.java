package com.example.model.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seat")
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer seatNumber;

    @Column(nullable = false)
    private Long stadium_id;

    @Column(nullable = false)
    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Seat(Long id, Integer seatNumber, Long stadium_id, String status) {
        this.id = id;
        this.seatNumber = seatNumber;
        this.stadium_id = stadium_id;
        this.status = status;
    }

    public Seat() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return seatNumber;
    }

    public Integer getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(Integer seatNumber) {
        this.seatNumber = seatNumber;
    }

    public Long getStadium_id() {
        return stadium_id;
    }

    public void setStadium_id(Long stadium_id) {
        this.stadium_id = stadium_id;
    }


}
