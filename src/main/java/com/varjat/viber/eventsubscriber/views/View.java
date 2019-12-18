package com.varjat.viber.eventsubscriber.views;

import com.varjat.viber.eventsubscriber.controllers.UserStateManager;
import com.varjat.viber.eventsubscriber.services.MessageService;

import java.util.List;

public interface View {
    String COLOR_POSITIVE = "#9DC2FF";
    String COLOR_NEGATIVE = "#FFB5BC";

    class ProcessingException extends RuntimeException {
        public UserStateManager.UserState state;

        public ProcessingException(UserStateManager.UserState state, Throwable cause) {
            super(cause);
            this.state = state;
        }
    }

    String getMessage();
    List<MessageService.Button> getButtons();
}
