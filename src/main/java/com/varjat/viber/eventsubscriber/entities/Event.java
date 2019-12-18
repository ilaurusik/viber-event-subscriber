package com.varjat.viber.eventsubscriber.entities;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Event {
    @Id @GeneratedValue
    public Long eventId;
    @Column(nullable = false)
    public String name;
    @Column(nullable = false)
    public String place;
    @Column(nullable = false)
    public LocalDateTime eventTime;
    @Column(nullable = false)
    public LocalDateTime createdOn = LocalDateTime.now();


    @OneToMany(
            mappedBy = "viberUser",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<ViberUserToEvent> user = new ArrayList<>();
}
