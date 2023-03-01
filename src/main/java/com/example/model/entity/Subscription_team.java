package com.example.model.entity;

import jakarta.persistence.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(name = "subscription_team")
public class Subscription_team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long teamID;

    @Column(nullable = false)
    private Long stadiumID;

    @Column(nullable = false)
    private Integer price;


    @Column(nullable = false)
    private Date date_of_start;

    @Column(nullable = false)
    private Date date_of_end;

    public Subscription_team(Long id, Long teamID, Long stadiumID, Integer price, Date date_of_start, Date date_of_end) {
        this.id = id;
        this.teamID = teamID;
        this.stadiumID = stadiumID;
        this.price = price;
        this.date_of_start = date_of_start;
        this.date_of_end = date_of_end;
    }

    public Date getDate_of_start() {
        return date_of_start;
    }


    public Date getDate_of_end() {
        return date_of_end;
    }


    public void setdate_of_start(String date_of_match) throws ParseException {
        this.date_of_start = new SimpleDateFormat("yyyy-MM-dd").parse(date_of_match);
    }
    public void setdate_of_end(String date_of_match) throws ParseException {
        this.date_of_end = new SimpleDateFormat("yyyy-MM-dd").parse(date_of_match);
    }

    public Subscription_team() {
    }

    @Override
    public String toString() {
        return "Subscription_team{" +
                "id=" + id +
                ", teamID=" + teamID +
                ", stadiumID=" + stadiumID +
                ", price=" + price +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTeamID() {
        return teamID;
    }

    public void setTeamID(Long teamID) {
        this.teamID = teamID;
    }

    public Long getStadiumID() {
        return stadiumID;
    }

    public void setStadiumID(Long stadiumID) {
        this.stadiumID = stadiumID;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}
