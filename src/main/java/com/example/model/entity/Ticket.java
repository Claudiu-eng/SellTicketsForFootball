package com.example.model.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long matchID;

    @Column(nullable = false,length = 30)
    private Long seatID;

    public Ticket() {
    }

    public Ticket(Long id, Long matchID, Long seatID) {
        this.id = id;
        this.matchID = matchID;
        this.seatID = seatID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatchID() {
        return matchID;
    }

    public void setMatchID(Long matchID) {
        this.matchID = matchID;
    }

    public Long getSeatID() {
        return seatID;
    }

    public void setSeatID(Long seatID) {
        this.seatID = seatID;
    }
}
