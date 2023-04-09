/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.personalinfoheaderservice.business;

import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.Channel;
import io.kubemq.sdk.event.Event;
import io.kubemq.sdk.tools.Converter;
import java.io.IOException;
import javax.net.ssl.SSLException;

/**
 *
 * @author student
 */
public class Messaging {
    public static void sendMessage(String message) throws IOException {
        final String channelName = "save_resume_channel",
                clientID = "save-resume-subscriber",
                kubeMQAddress = System.getenv("KUBEMQ_ADDRESS");
        
        System.out.println("KubeMQ: " + kubeMQAddress);
        
        Channel channel = new Channel(channelName, clientID, false, kubeMQAddress);
        channel.setStore(true);
        Event event = new Event();
        event.setBody(Converter.ToByteArray(message));
        event.setEventId("event-Store-");
        try {
          channel.SendEvent(event);
        } catch (SSLException e) {
            System.out.printf("SSLException: %s\n", e.getMessage());
        } catch (ServerAddressNotSuppliedException e) {
            System.out.printf("ServerAddressNotSuppliedException: %s\n", e.getMessage());
        }
        
    }
}
