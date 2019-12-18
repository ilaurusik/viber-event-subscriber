package com.varjat.viber.eventsubscriber.repository;

import com.varjat.viber.eventsubscriber.entities.ViberUserToEvent;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ViberUserToEventRepository extends CrudRepository<ViberUserToEvent, ViberUserToEvent.ViberUserToEventId> {
}
