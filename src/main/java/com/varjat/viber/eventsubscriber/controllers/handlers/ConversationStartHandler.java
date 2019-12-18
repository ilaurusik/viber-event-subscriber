package com.varjat.viber.eventsubscriber.controllers.handlers;

import com.varjat.viber.eventsubscriber.config.ViberProperties;
import com.varjat.viber.eventsubscriber.controllers.UserStateManager;
import com.varjat.viber.eventsubscriber.controllers.events.Viber;
import com.varjat.viber.eventsubscriber.services.MessageService;
import com.varjat.viber.eventsubscriber.views.View;
import com.varjat.viber.eventsubscriber.views.admin.AdminRouter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConversationStartHandler implements EventHandler<Viber.ConversationStartedEvent> {

    @Autowired
    private MessageService messageService;

    @Autowired
    private ViberProperties viberProperties;

    @Autowired
    private UserStateManager userStateManager;

    @Autowired
    private AdminRouter adminRouter;

    @Override
    public void handleEvent(Viber.ConversationStartedEvent event) throws HandleErrorException {
        try {
            String userId = event.user.id;
            UserStateManager.UserState state = userStateManager.getState(userId);
            if (state == null) {
                state = new UserStateManager.UserState(adminRouter.buildAdminView(userId), null);
                userStateManager.updateState(userId, state);
            }

            messageService.showView(userId, state.currentView);

        } catch (IOException e) {
            throw new HandleErrorException(e.getMessage());
        }
    }
}
