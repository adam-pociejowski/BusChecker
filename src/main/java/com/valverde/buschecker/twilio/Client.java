package com.valverde.buschecker.twilio;


import com.twilio.http.TwilioRestClient;

public class Client {
    private TwilioCredentials credentials;
    private TwilioMessageCreator messageCreator;

    public Client() {
        this.credentials = new TwilioCredentials();
        this.messageCreator = new TwilioMessageCreator(
                new TwilioRestClient.Builder(credentials.getAccountSid(), credentials.getAuthToken()).build()
        );
    }

    public void sendMessage(String to, String message) {
        messageCreator.create(to, credentials.getPhoneNumber(), message);
    }
}