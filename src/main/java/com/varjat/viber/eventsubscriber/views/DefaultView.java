package com.varjat.viber.eventsubscriber.views;

import com.varjat.viber.eventsubscriber.controllers.UserStateManager.UserState;
import com.varjat.viber.eventsubscriber.controllers.events.Viber;
import com.varjat.viber.eventsubscriber.services.MessageService;
import com.varjat.viber.eventsubscriber.views.admin.AdminEvents;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class DefaultView implements View {

    private String message;
    private List<ViewButton> buttons = new ArrayList<>();

    public Map<AdminEvents, BiFunction<Viber.MessageEvent, UserState, UserState>> onPressButtonHandlers = new HashMap<>();
    public Map<AdminEvents, BiFunction<ProcessingException, Viber.MessageEvent, UserState>> onPressButtonErrorHandlers = new HashMap<>();

    //private BiFunction<Viber.MessageEvent, GlobalStateManager.State, GlobalStateManager.State> messageReceiveFunction = (messageEvent, state) -> state;
    public BiFunction<Viber.MessageEvent, UserState, UserState> onMessage = (messageEvent, state) -> state;
    public BiFunction<Viber.MessageEvent, ProcessingException, UserState> onError = ((messageEvent, e) -> e.state);

    public DefaultView(String message, ViewButton... buttons) {
        this.message = message;
        this.buttons.addAll(Arrays.asList(buttons));
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public List<MessageService.Button> getButtons() {
        return buttons.stream().map(b -> new MessageService.Button(b.event.name(), b.title, b.bgColor)).collect(Collectors.toList());
    }

//    public DefaultView onMessageReceive(BiFunction<Viber.MessageEvent, GlobalStateManager.State, GlobalStateManager.State> function) {
//        this.messageReceiveFunction = function;
//        return this;
//    }

    public DefaultView onMessage(BiFunction<Viber.MessageEvent, UserState, UserState> function) {
        this.onMessage = function;
        return this;
    }

    public DefaultView onError(BiFunction<Viber.MessageEvent, ProcessingException, UserState> function) {
        this.onError = function;
        return this;
    }

    public DefaultView addButton(ViewButton viewButton) {
        if (viewButton.onPressButton != null) {
            onPressButtonHandlers.put(viewButton.event, viewButton.onPressButton);
        }
        if (viewButton.onError != null) {
            onPressButtonErrorHandlers.put(viewButton.event, viewButton.onError);
        }
        buttons.add(viewButton);
        return this;
    }

    public static class ViewButton {
        public static String COLOR_GREEN = "#9DC2FF";
        public static String COLOR_RED = "#FFB5BC";

        private String bgColor;
        private String title;
        private AdminEvents event;
        private String trackingData;

        public ViewButton(String title, AdminEvents event, String trackingData, String bgColor) {
            this.bgColor = bgColor;
            this.title = title;
            this.event = event;
            this.trackingData = trackingData;
        }

        public ViewButton(String title, AdminEvents event, String bgColor) {
            this(title, event, null, bgColor);
        }

        private BiFunction<Viber.MessageEvent, UserState, UserState> onPressButton;
        private BiFunction<ProcessingException, Viber.MessageEvent, UserState> onError;

        public ViewButton onPressButton(BiFunction<Viber.MessageEvent, UserState, UserState> function) {
            this.onPressButton = function;
            return this;
        }

        public ViewButton onError(BiFunction< ProcessingException, Viber.MessageEvent, UserState> function) {
            this.onError = function;
            return this;
        }
    }



}
