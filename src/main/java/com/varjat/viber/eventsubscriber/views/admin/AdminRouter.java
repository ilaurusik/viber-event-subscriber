package com.varjat.viber.eventsubscriber.views.admin;

import com.varjat.viber.eventsubscriber.controllers.UserStateManager;
import com.varjat.viber.eventsubscriber.controllers.UserStateManager.CreateEventData;
import com.varjat.viber.eventsubscriber.controllers.UserStateManager.UserState;
import com.varjat.viber.eventsubscriber.services.MessageService;
import com.varjat.viber.eventsubscriber.views.DefaultView;
import com.varjat.viber.eventsubscriber.views.DefaultView.ViewButton;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAccessor;
import java.util.Locale;

import static com.varjat.viber.eventsubscriber.views.DefaultView.ViewButton.COLOR_GREEN;
import static com.varjat.viber.eventsubscriber.views.DefaultView.ViewButton.COLOR_RED;
import static com.varjat.viber.eventsubscriber.views.admin.AdminEvents.*;

@Component
public class AdminRouter {

    @Autowired
    private MessageService messageService;

    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");

    public DefaultView buildAdminView(String userId) {
        DefaultView mainView = new DefaultView("Приветствуем вас в группе 'Воскресный хоккей'! Вы - администратор");

        mainView
            .addButton(
                new ViewButton("Создать игру", create_event, COLOR_GREEN)
                        .onPressButton((message, state) ->
                            new UserState(
                                    createPlaceView(userId, mainView),
                                    new CreateEventData(userId, message.message.text, null)))
            )
            .addButton(
                new ViewButton("Посмотреть список", view_status, COLOR_GREEN)
                        .onPressButton((message, state) -> {
                                messageService.sendMessage(userId, "1. Андрей Иванов\r\n2. Сергей Петров\r\n3. Олег Сидоров", null, null);
                                return state;
                        })
            )
            .addButton(new ViewButton("Отменить игру", cancel_event, COLOR_RED));


        return mainView;
    }

    private DefaultView createPlaceView(String userId, DefaultView mainView) {
        return new DefaultView("Укажите место, где планируется игра? (например: Конькобежка, Малая Чижовка и т.п.)")
                .addButton(createCancelButton(userId, mainView))
                .onMessage((placeReply, s2) ->
                        new UserState(
                            createTimeView(userId, mainView),
                            new CreateEventData(userId, placeReply.message.text, null)
                        )
                );
    }

    private DefaultView createTimeView(String userId, DefaultView startView) {
        return
            new DefaultView("Укажите время в формате ЧЧ.ММ.ГГГГ чч:мм, когда планируется игра? (например: '12.03.2019 19:15')")
                .addButton(createCancelButton(userId, startView))
                .onMessage((timeMessage, state) -> {

                    TemporalAccessor parse = formatter.parse(timeMessage.message.text);
                    LocalDateTime gameDate = LocalDateTime.from(parse);
                    // save to db
                    String message = "Запланирована игра! \r\n" +
                            "Время: " + DateTimeFormatter.ofPattern("EEE, dd LLLL, 'в' HH:mm").withLocale(new Locale("ru", "BY")).format(gameDate) + "\r\n" +
                            "Место: " + state.createEventData.eventPlace;
                    messageService.sendMessage(userId, message, null, null);

                    return new UserState(
                            startView,
                            null
                    );
                })
                .onError((message, e) -> {
                    e.printStackTrace();
                    if (e.getCause() instanceof DateTimeParseException) {
                        messageService.sendMessage(userId, "Не верный формат даты и времени. Попробуйте ещё раз", null, null);
                    }
                    return e.state;
                });
    }

    private ViewButton createCancelButton(String userId, DefaultView mainView) {
        return new ViewButton("Отмена", cancel_current_operation, COLOR_RED)
            .onPressButton((cancelButtonMessage, state) -> {
                messageService.sendMessage(userId, "Создание игры прекращено", null, null);
                return new UserState(mainView, null);
            });
    }

    public static void main(String[] args) {
        TemporalAccessor parse = null;
        try {

         parse = formatter.parse("10.12.1983 10:00");
         String res = DateTimeFormatter.ofPattern("EEE, dd LLLL, 'в' HH:mm").withLocale(new Locale("ru", "BY")).format(LocalDateTime.from(parse));
            System.out.println(res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
