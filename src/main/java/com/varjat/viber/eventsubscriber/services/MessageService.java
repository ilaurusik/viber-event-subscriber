package com.varjat.viber.eventsubscriber.services;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.varjat.viber.eventsubscriber.controllers.events.Viber;
import com.varjat.viber.eventsubscriber.views.View;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public interface MessageService {

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    class ViberTextMessageRequest {
        public String receiver;
        public int minApiVersion = 1;
        public Viber.Sender sender;
        public String trakingData;
        public String type = "text";
        public String text;
        public Keyboard keyboard;
    }

    @JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
    class ViberMessageResponse {
        public int status;
        public String statusMessage;
        public String messageToken;
        public String chatHostname;
    }

    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    class Button {

        public Button(String actionBody, String text, String bgColor) {
            this.actionBody = actionBody;
            this.text = text;
            this.bgColor = bgColor;
        }

        public Button() {}

        public int columns = 6;
        public int rows = 1;
        public String actionType = "reply";
        public String actionBody = "";
        public String textSize = "regular";
        public String text = "text";
        public String bgColor;
    }
    @JsonNaming(PropertyNamingStrategy.UpperCamelCaseStrategy.class)
    class Keyboard {
        public String type = "keyboard";
        public boolean defaultHeight = false;
        public List<Button> buttons = new LinkedList<>();
    }

    void sendMessage(String userId, String message, String trackingMessage, Keyboard keyboard);

    void showView(String userId, View view) throws IOException;
}
