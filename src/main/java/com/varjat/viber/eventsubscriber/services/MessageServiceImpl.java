package com.varjat.viber.eventsubscriber.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varjat.viber.eventsubscriber.config.ViberProperties;
import com.varjat.viber.eventsubscriber.views.View;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    private HttpClient httpClient;

    @Autowired
    private ViberProperties config;


    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public void sendMessage(String userId, String message, String trackingMessage, Keyboard keyboard) {

        HttpPost httpPost = new HttpPost("https://chatapi.viber.com/pa/send_message");
        httpPost.addHeader("X-Viber-Auth-Token", config.getAuthKey());
        httpPost.addHeader("content-type", "application/json");
        httpPost.addHeader("encoding", "UTF-8");


        ViberTextMessageRequest messageRequest = new ViberTextMessageRequest();
//        Viber.Sender sender = new Viber.Sender();
//        sender.name = "God";
//        messageRequest.sender = sender;
        messageRequest.receiver = userId;
        messageRequest.trakingData = trackingMessage;
        messageRequest.text = message;
        messageRequest.keyboard = keyboard;


        try {
            httpPost.setEntity(new StringEntity(mapper.writeValueAsString(messageRequest), "UTF-8"));

            HttpResponse execute = httpClient.execute(httpPost);
            ViberMessageResponse viberMessageResponse = mapper.readValue(execute.getEntity().getContent(), ViberMessageResponse.class);
            System.out.println(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(viberMessageResponse));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void showView(String userId, View view) {
        Keyboard keyboard = null;
        if (view.getButtons().size() > 0) {
            keyboard = new Keyboard();
            keyboard.buttons = view.getButtons();
        }

        sendMessage(userId, view.getMessage(), null, keyboard);
    }
}
