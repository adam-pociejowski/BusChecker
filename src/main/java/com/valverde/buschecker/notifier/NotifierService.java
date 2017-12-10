package com.valverde.buschecker.notifier;

public class NotifierService {

    public NotifyStatus sendSms(final String message, final String recipients) {
        return NotifyStatus.SUCCESS;
    }

    public NotifierService(final String username, final String password) {
        this.username = username;
        this.password = password;
    }


    private final String username;

    private final String password;
}