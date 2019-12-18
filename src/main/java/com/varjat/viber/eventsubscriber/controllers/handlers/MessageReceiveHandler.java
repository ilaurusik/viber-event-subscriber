package com.varjat.viber.eventsubscriber.controllers.handlers;

import com.varjat.viber.eventsubscriber.config.ViberProperties;
import com.varjat.viber.eventsubscriber.controllers.UserStateManager;
import com.varjat.viber.eventsubscriber.controllers.events.Viber;
import com.varjat.viber.eventsubscriber.services.MessageService;
import com.varjat.viber.eventsubscriber.views.DefaultView;
import com.varjat.viber.eventsubscriber.views.View;
import com.varjat.viber.eventsubscriber.views.admin.AdminEvents;
import com.varjat.viber.eventsubscriber.views.admin.AdminRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageReceiveHandler implements EventHandler<Viber.MessageEvent> {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ViberProperties viberProperties;

    @Autowired
    private AdminRouter adminRouter;

    @Autowired
    private UserStateManager userStateManager;

    @Override
    public void handleEvent(Viber.MessageEvent event) throws HandleErrorException {
        try {
            String userId = event.sender.id;
            UserStateManager.UserState state = userStateManager.getState(userId);
            if (state == null) {
                state = new UserStateManager.UserState(adminRouter.buildAdminView(userId), null);
                userStateManager.updateState(userId, state);
            }
            DefaultView currentView = state.currentView;
            UserStateManager.UserState newState;

            AdminEvents action = null;
            try {
                action = AdminEvents.valueOf(event.message.text);
            } catch (IllegalArgumentException e) {}

            if (action == null) {
                try {
                    newState = currentView.onMessage.apply(event, state);
                } catch (Exception e) {
                    newState = currentView.onError.apply(event, new View.ProcessingException(state, e));
                }
            } else {

                try {
                    newState = currentView.onPressButtonHandlers.getOrDefault(action, (messageEvent, oldState) -> oldState).apply(event, state);
                } catch (Exception e) {
                    newState = currentView.onPressButtonErrorHandlers.getOrDefault(action, (e1, messageEvent) -> e1.state).apply(new View.ProcessingException(state, e), event);
                }
            }

            userStateManager.updateState(userId, newState);
            messageService.showView(userId, newState.currentView);

        } catch (IOException e) {
            throw new HandleErrorException(e.getMessage());
        }
    }

}
