/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.resumeholic.documentservice.business;

import com.resumeholic.documentservice.endpoint.ResumeholicServletContextListener;
import com.resumeholic.documentservice.persistence.ResumeCRUD;
import io.grpc.stub.StreamObserver;
import io.kubemq.sdk.basic.ServerAddressNotSuppliedException;
import io.kubemq.sdk.event.EventReceive;
import io.kubemq.sdk.event.Subscriber;
import io.kubemq.sdk.subscription.EventsStoreType;
import io.kubemq.sdk.subscription.SubscribeRequest;
import io.kubemq.sdk.subscription.SubscribeType;
import io.kubemq.sdk.tools.Converter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLException;

/**
 *
 * @author student
 */
public class Messaging {
    public static void Receiving_Events_Store(String cname) throws SSLException, ServerAddressNotSuppliedException {
        final String ChannelName = cname, ClientID = "document-service-subscriber";
                String kubeMQAddress = System.getenv("KUBEMQ_ADDRESS");
                
        Subscriber subscriber = new Subscriber(kubeMQAddress);
        SubscribeRequest subscribeRequest = new SubscribeRequest();
        subscribeRequest.setChannel(ChannelName);
        subscribeRequest.setClientID(ClientID);
        subscribeRequest.setSubscribeType(SubscribeType.EventsStore);
        subscribeRequest.setEventsStoreType(EventsStoreType.StartAtSequence);
        subscribeRequest.setEventsStoreTypeValue(1);

        StreamObserver<EventReceive> streamObserver = new StreamObserver<EventReceive>() {

            @Override
            public void onNext(EventReceive value) {
                try {
                    String val=(String) Converter.FromByteArray(value.getBody());
                    System.out.printf("Event Received: EventID: %s, Channel: %s, Metadata: %s, Body: %s\n",
                            value.getEventId(), value.getChannel(), value.getMetadata(),
                            Converter.FromByteArray(value.getBody()));
                    String[] msgParts = val.split("\\Q|\\E");
                    System.out.println("Received " + msgParts.length + " parts");
                    if (msgParts.length == 3 && msgParts[0].equals("PersonalInfoHeaderSaved")) {
                        try {
                            int resumeId = Integer.parseInt(msgParts[1]);
                            ZonedDateTime updateDateTime = ZonedDateTime.parse(msgParts[2], DateTimeFormatter.ISO_DATE_TIME);
                            System.out.println("Saving resume info...");
                            ResumeCRUD.update(resumeId, updateDateTime);
                            System.out.println("Resume info saved! ID: " + resumeId);
                        } catch (NumberFormatException ex) {
                            System.out.println("Invalid resume id: " + msgParts[1]);
                            System.out.println(ex);
                        }
                    }
                    
                } catch (ClassNotFoundException e) {
                    System.out.printf("ClassNotFoundException: %s", e.getMessage());
                } catch (IOException e) {
                    System.out.printf("IOException: %s", e.getMessage());
                }  
            }

            @Override
            public void onError(Throwable t) {
                System.out.printf("onError:  %s", t.getMessage());
            }

            @Override
            public void onCompleted() {

            }

        };
        subscriber.SubscribeToEvents(subscribeRequest, streamObserver);

    }
}
