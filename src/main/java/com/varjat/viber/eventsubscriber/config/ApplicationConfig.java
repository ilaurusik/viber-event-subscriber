package com.varjat.viber.eventsubscriber.config;

import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean(destroyMethod = "close")
    public HttpClient httpClient(){
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager();
        return HttpClients.custom().setConnectionManager(connManager).build();
    }

    @Bean
    @ConfigurationProperties(prefix = "viber")
    public ViberProperties viberProperties (){
        return new ViberProperties();
    }
}
