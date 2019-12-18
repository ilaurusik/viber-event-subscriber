package com.varjat.viber.eventsubscriber.repository;

import com.varjat.viber.eventsubscriber.entities.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<Event, Long> {
}
