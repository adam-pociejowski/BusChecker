package com.valverde.buschecker.notification;


import com.twilio.http.TwilioRestClient;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;

public class SmsCreator {

    private final TwilioRestClient client;

    public SmsCreator(TwilioRestClient client) {
        this.client = client;
    }

    public Message create(String to, String from, String body) {
        MessageCreator messageCreator = new MessageCreator(
                new PhoneNumber(to),
                new PhoneNumber(from),
                body);

        return messageCreator.create(this.client);
    }
}