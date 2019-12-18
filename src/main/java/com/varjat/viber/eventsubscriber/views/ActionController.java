package com.varjat.viber.eventsubscriber.views;

import com.varjat.viber.eventsubscriber.controllers.events.Viber;

import java.io.IOException;

public interface ActionController {
    View processAction(Viber.MessageEvent message) throws IOException;
}
