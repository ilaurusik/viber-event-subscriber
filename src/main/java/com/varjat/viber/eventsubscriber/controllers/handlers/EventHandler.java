package com.varjat.viber.eventsubscriber.controllers.handlers;

import com.varjat.viber.eventsubscriber.controllers.events.Viber;

public interface EventHandler<T extends Viber.Event> {
    class HandleErrorException extends Exception {
        public HandleErrorException(String message) {
            super(message);
        }
    }

    void handleEvent(T event) throws HandleErrorException;
}
