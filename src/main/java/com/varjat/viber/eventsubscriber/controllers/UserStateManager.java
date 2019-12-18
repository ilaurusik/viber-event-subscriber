package com.varjat.viber.eventsubscriber.controllers;

import com.varjat.viber.eventsubscriber.views.DefaultView;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class UserStateManager {

    public static class UserState {
        public DefaultView currentView;
        public CreateEventData createEventData;

        public UserState(DefaultView currentView, CreateEventData createEventData) {
            this.currentView = currentView;
            this.createEventData = createEventData;
        }
    }

    public static class CreateEventData {
        public String userId;
        public String eventPlace;
        public LocalDateTime eventTime;

        public CreateEventData(String userId, String eventPlace, LocalDateTime eventTime) {
            this.userId = userId;
            this.eventPlace = eventPlace;
            this.eventTime = eventTime;
        }
    }

    private Map<String, UserState> currentState = new ConcurrentHashMap<>();

    public UserState getState(String userId) {
        return currentState.get(userId);
    }

    public void updateState(String userId, UserState state) {
        currentState.put(userId, state);
    }
}
