package com.example.model.entity;


import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Entity
@Table(name = "eveniment")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long awayTeamID;

    @Column(nullable = false)
    private Long homeTeamID;

    @Column(nullable = false)
    private Integer price;

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }


    @Override
    public String toString() {
        return "Match{" +
                "id=" + id +
                ", awayTeamID=" + awayTeamID +
                ", homeTeamID=" + homeTeamID +
                ", stadiumID=" + stadiumID +
                ", date_of_match=" + date_of_match +
                '}';
    }

    @Column(nullable = false)
    private Long stadiumID;

    @Column(nullable = false)
    private Date date_of_match;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAwayTeamID() {
        return awayTeamID;
    }

    public void setAwayTeamID(Long awayTeamID) {
        this.awayTeamID = awayTeamID;
    }

    public Long getHomeTeamID() {
        return homeTeamID;
    }

    public void setHomeTeamID(Long homeTeamID) {
        this.homeTeamID = homeTeamID;
    }

    public Long getStadiumID() {
        return stadiumID;
    }

    public void setStadiumID(Long stadiumID) {
        this.stadiumID = stadiumID;
    }

    public Date getDate_of_match() {
        return date_of_match;
    }

    public void setDate_of_match(String date_of_match) throws ParseException {
        this.date_of_match = new SimpleDateFormat("yyyy-MM-dd").parse(date_of_match);
    }

    public void setDate(Date date_of_match) {
        this.date_of_match = date_of_match;
    }

    public Match() {
    }

    public Match(Long id, Long awayTeamID, Long homeTeamID, Long stadiumID, Date date_of_match,Integer price) {
        this.id = id;
        this.awayTeamID = awayTeamID;
        this.homeTeamID = homeTeamID;
        this.stadiumID = stadiumID;
        this.date_of_match = date_of_match;
        this.price = price;
    }
}
