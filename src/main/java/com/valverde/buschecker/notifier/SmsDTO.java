package com.valverde.buschecker.notifier;

import lombok.Data;

@Data
public class SmsDTO {

    private String to;

    private String message;

}
