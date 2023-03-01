package com.example.model.entity;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "subscription")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer price;

    @Column(nullable = false,length = 30)
    private Long stadiumID;

    @Column(nullable = false,length = 30)
    private Long teamID;

    @Column()
    private Long seatID;

    @Column(nullable = false)
    private Date date_of_start;

    @Column(nullable = false)
    private Date date_of_end;

    public Date getDate_of_start() {
        return date_of_start;
    }

    public void setDate_of_start(Date date_of_start) {
        this.date_of_start = date_of_start;
    }

    public Date getDate_of_end() {
        return date_of_end;
    }

    public void setDate_of_end(Date date_of_end) {
        this.date_of_end = date_of_end;
    }

    public Subscription(Long id, Integer price, Long stadiumID, Long teamID, Long seatID, Date date_of_start, Date date_of_end) {
        this.id = id;
        this.price = price;
        this.stadiumID = stadiumID;
        this.teamID = teamID;
        this.seatID = seatID;
        this.date_of_start = date_of_start;
        this.date_of_end = date_of_end;
    }

    public Subscription() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Long getStadiumID() {
        return stadiumID;
    }

    public void setStadiumID(Long stadiumID) {
        this.stadiumID = stadiumID;
    }

    public Long getTeamID() {
        return teamID;
    }

    public void setTeamID(Long teamID) {
        this.teamID = teamID;
    }

    public Long getSeatID() {
        return seatID;
    }

    public void setSeatID(Long seatID) {
        this.seatID = seatID;
    }
}
