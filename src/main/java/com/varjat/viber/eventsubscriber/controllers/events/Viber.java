package com.varjat.viber.eventsubscriber.controllers.events;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import java.math.BigDecimal;

public class Viber {
    public enum ViberEventType {
        delivered,
        seen,
        failed,
        subscribed,
        unsubscribed,
        conversation_started,
        message,
        webhook
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "event",
            defaultImpl = SimpleEvent.class,
            visible = true)
    @JsonSubTypes({
            @JsonSubTypes.Type(value = MessageEvent.class, name = "message"),
            @JsonSubTypes.Type(value = ConversationStartedEvent.class, name = "conversation_started"),
    })
    public static abstract class Event {
        public ViberEventType event;
        public long timestamp;
        public long messageToken;
    }

    public static class SimpleEvent extends Event {
        public String userId;
    }

    public static class MessageEvent extends Event {
        public Sender sender;
        public Message message;
    }

    public static class Message {
        public String type;
        public String text;
        public String media;
        public Location location;
        public String trackingData;
    }
    public static class Location {
        public BigDecimal lat;
        public BigDecimal lon;
    }
    public static class Sender {
        public String id;
        public String name;
        public String avatar;
        public String country;
        public String language;
        public String apiVersion;
    }

    public static class ConversationStartedEvent extends Event {
        public String type; //"open",
        public String context; //"context information",
        public Sender user;
        public Boolean subscribed;
    }
}
