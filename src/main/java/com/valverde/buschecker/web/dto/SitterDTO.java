package com.valverde.buschecker.web.dto;

import com.valverde.buschecker.entity.Sitter;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SitterDTO {

    private String firstname;

    private String lastname;

    SitterDTO(Sitter sitter) {
        this.firstname = sitter.getFirstname();
        this.lastname = sitter.getLastname();
    }
}