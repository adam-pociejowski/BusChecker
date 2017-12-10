package com.valverde.buschecker.web.dto;

import lombok.Data;
import java.util.Date;

@Data
public class ReviewDTO {

    private String busName;

    private String reviewName;

    private Date reviewDate;

    private Date lastReviewDate;

    private String state;
}
