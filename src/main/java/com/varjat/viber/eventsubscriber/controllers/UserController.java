package com.varjat.viber.eventsubscriber.controllers;

import com.varjat.viber.eventsubscriber.entities.ViberUser;
import com.varjat.viber.eventsubscriber.repository.EventRepository;
import com.varjat.viber.eventsubscriber.repository.ViberUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private ViberUserRepository viberUserRepository;

    @GetMapping("/add")
    public void createEvent(){

        eventRepository.findById(1L)
                .ifPresent(e -> {

                    ViberUser user = new ViberUser();
                    user.accountId = "123-456-789==";
                    user.name = "Foo Bar";
                    //user.events.add(e);

                    //e..add(user);
                    viberUserRepository.save(user);
                    eventRepository.save(e);
                });
    }

}
