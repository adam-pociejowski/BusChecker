package com.valverde.buschecker.web.dto;

import lombok.Data;

@Data
public class SmsDTO {

    private String recipients;

    private String message;
}
