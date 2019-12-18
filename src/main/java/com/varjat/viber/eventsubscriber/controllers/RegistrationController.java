package com.varjat.viber.eventsubscriber.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.varjat.viber.eventsubscriber.config.ViberProperties;
import com.varjat.viber.eventsubscriber.controllers.exceptions.BadRequestException;
import com.varjat.viber.eventsubscriber.services.MessageService;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class RegistrationController {

    @Autowired
    private ViberProperties config;

    @Autowired
    private MessageService messageService;

    @GetMapping("/webhook/add")
    public String addWebhook() throws BadRequestException {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("https://chatapi.viber.com/pa/set_webhook");
            httpPost.addHeader("X-Viber-Auth-Token", config.getAuthKey());
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> request = new HashMap<>();
            request.put("url", "https://8ada56d2.ngrok.io");
            request.put("send_name", true);
            request.put("send_photo", false);

            HttpEntity stringEntity = new StringEntity(mapper.writeValueAsString(request), ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpclient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "UTF-8");
            return result;
        } catch (IOException e) {
            e.printStackTrace();
        }
        throw new BadRequestException();
    }


    @GetMapping("/message")
    public void sendMessage(){
        messageService.sendMessage("KWWAh2ZDM8iZS8bsefaNrg==", "Provet, pridurok!", null, null);
    }
}
