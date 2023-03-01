package com.example.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "team")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long championshipID;

    @Column(nullable = false,length = 30)
    private String name;

    public Team(Long id, Long championshipID, String name) {
        this.id = id;
        this.championshipID = championshipID;
        this.name = name;
    }

    public Team() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getChampionshipID() {
        return championshipID;
    }

    public void setChampionshipID(Long championshipID) {
        this.championshipID = championshipID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Team{" +
                "id=" + id +
                ", championshipID=" + championshipID +
                ", name='" + name + '\'' +
                '}';
    }
}
