package com.varjat.viber.eventsubscriber.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.varjat.viber.eventsubscriber.controllers.events.Viber;
import com.varjat.viber.eventsubscriber.controllers.handlers.ConversationStartHandler;
import com.varjat.viber.eventsubscriber.controllers.handlers.EventHandler;
import com.varjat.viber.eventsubscriber.controllers.handlers.MessageReceiveHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RootController {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Autowired
    private ConversationStartHandler conversationStartHandler;

    @Autowired
    private MessageReceiveHandler messageReceiveHandler;

    @RequestMapping("/")
    public void handle(@RequestBody Viber.Event event) throws JsonProcessingException, EventHandler.HandleErrorException {

        switch (event.event) {
            case message:
                messageReceiveHandler.handleEvent((Viber.MessageEvent) event);
                /*Viber.MessageEvent messageEvent = (Viber.MessageEvent) event;
                System.out.println("User "+ messageEvent.sender.id+ " says '"+messageEvent.message.text+"' ("+messageEvent.messageToken+")");
                */
                break;
            case subscribed:
                break;
            case conversation_started:
                conversationStartHandler.handleEvent((Viber.ConversationStartedEvent) event);
                break;
            case seen:
            case failed:
            case delivered:
            case unsubscribed:
                Viber.SimpleEvent simpleEvent = (Viber.SimpleEvent) event;
                System.out.println("User "+simpleEvent.userId+ " do "+simpleEvent.event+" for message "+simpleEvent.messageToken);
                break;
            default:
                System.out.println("/************************************************************/");
                System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(event));
                break;
        }
    }
}
