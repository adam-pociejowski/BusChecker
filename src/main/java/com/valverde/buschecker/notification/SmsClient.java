package com.valverde.buschecker.notification;


import com.twilio.http.TwilioRestClient;

public class SmsClient {
    private SmsCredentials credentials;
    private SmsCreator messageCreator;

    public SmsClient() {
        this.credentials = new SmsCredentials();
        this.messageCreator = new SmsCreator(
                new TwilioRestClient.Builder(credentials.getAccountSid(), credentials.getAuthToken()).build()
        );
    }

    public void sendMessage(String to, String message) {
        messageCreator.create(to, credentials.getPhoneNumber(), message);
    }
}