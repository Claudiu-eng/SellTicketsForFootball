package com.example.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "stadium")
public class Stadium {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Integer capacity;

    @Column(nullable = false,length = 30)
    private String address;

    @Column(nullable = false,length = 30)
    private String name;

    public Stadium(Long id, Integer capacity, String address,String name) {
        this.id = id;
        this.capacity = capacity;
        this.address = address;
        this.name=name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Stadium() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
