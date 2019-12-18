package com.varjat.viber.eventsubscriber.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.NaturalId;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.HashSet;
import java.util.Set;

@Entity
public class ViberUser {

    @Id @GeneratedValue
    public Long viberUserId;

    @NaturalId
    public String accountId;

    public String name;

//    @JsonIgnore
//    @ManyToMany(mappedBy = "participants")
//    public Set<Event> events = new HashSet<>();

}
