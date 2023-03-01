package com.example.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "championship")
public class Championship {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer numberOfTeams;

    @Column(nullable = false)
    private Integer maximNumberOfTeams;

    @Column(nullable = false,length = 30,unique = true)
    private String name;

    public Championship() {
    }

    @Override
    public String toString() {
        return "Championship{" +
                "id=" + id +
                ", numberOfTeams=" + numberOfTeams +
                ", maximNumberOfTeams=" + maximNumberOfTeams +
                ", name='" + name + '\'' +
                '}';
    }

    public Championship(Integer id, Integer numberOfTeams, String name, Integer maximNumberOfTeams) {
        this.id = id;
        this.numberOfTeams = numberOfTeams;
        this.name = name;
        this.maximNumberOfTeams=maximNumberOfTeams;
    }

    public Integer getId() {
        return id;
    }

    public Integer getMaximNumberOfTeams() {
        return maximNumberOfTeams;
    }

    public void setMaximNumberOfTeams(Integer maximNumberOfTeams) {
        this.maximNumberOfTeams = maximNumberOfTeams;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumberOfTeams() {
        return numberOfTeams;
    }

    public void setNumberOfTeams(Integer numberOfTeams) {
        this.numberOfTeams = numberOfTeams;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
