package com.varjat.viber.eventsubscriber.repository;

import com.varjat.viber.eventsubscriber.entities.Event;
import com.varjat.viber.eventsubscriber.entities.ViberUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViberUserRepository extends CrudRepository<ViberUser, String> {
}
