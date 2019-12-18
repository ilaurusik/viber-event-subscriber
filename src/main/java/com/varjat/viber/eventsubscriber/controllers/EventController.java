package com.varjat.viber.eventsubscriber.controllers;

import com.varjat.viber.eventsubscriber.controllers.exceptions.NotFoundException;
import com.varjat.viber.eventsubscriber.entities.Event;
import com.varjat.viber.eventsubscriber.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/event")
public class EventController {

    @Autowired
    private EventRepository eventRepository;

    @GetMapping("/{id}")
    public Event list(@PathVariable Long id) throws NotFoundException {
        return eventRepository.findById(id)
                .orElseThrow(() -> new NotFoundException());
    }

    @GetMapping("/create")
    public Event createEvent(){

        Event event = new Event();
        event.name = "Event";

        return eventRepository.save(event);
    }


}
