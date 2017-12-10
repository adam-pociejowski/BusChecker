package com.valverde.buschecker.service;

import com.valverde.buschecker.entity.Bus;
import com.valverde.buschecker.entity.Driver;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.enums.ReviewType;
import com.valverde.buschecker.util.NotificationUtils;
import com.valverde.buschecker.web.dto.ReviewDTO;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReviewService {

    public List<ReviewDTO> createReviewsListForUser(User user) {
        List<ReviewDTO> reviewList = new ArrayList<>();
        for (Driver driver : user.getDrivers()) {
            reviewList.addAll(getReviewListForDriver(driver));
        }
        reviewList.sort(new Comparator<ReviewDTO>() {
            @Override
            public int compare(ReviewDTO o1, ReviewDTO o2) {
                return o1.getReviewDate().compareTo(o2.getReviewDate());
            }
        });
        return reviewList;
    }

    private List<ReviewDTO> getReviewListForDriver(Driver driver) {
        List<ReviewDTO> reviewList = new ArrayList<>();
        for (Bus bus : driver.getBuses()) {
            addReviewElement(bus.getExtinguisherReviewDate(), ReviewType.EXTIGUISHERY, bus.getBusName(), reviewList);
            addReviewElement(bus.getLiftReviewDate(), ReviewType.LIFT, bus.getBusName(), reviewList);
            addReviewElement(bus.getTachographReviewDate(), ReviewType.TACHOGRAPH, bus.getBusName(), reviewList);
            addReviewElement(bus.getTechnicalReviewDate(), ReviewType.TECHNICAL, bus.getBusName(), reviewList);
            addReviewElement(bus.getInsuranceDate(), ReviewType.INSURANCE, bus.getBusName(), reviewList);
        }
        return reviewList;
    }

    private void addReviewElement(Date reviewDate, ReviewType type, String busName,
                                       List<ReviewDTO> reviewList) {
        if (reviewDate != null && reviewDate.after(TWO_THOUSANDS_DATE)) {
            Date nextReviewDate = NotificationUtils.getNextReviewDate(reviewDate, type.getMonth());
            ReviewDTO reviewDTO = new ReviewDTO();
            reviewDTO.setBusName(busName);
            reviewDTO.setLastReviewDate(reviewDate);
            reviewDTO.setReviewDate(nextReviewDate);
            reviewDTO.setReviewName(type.getName());
            reviewList.add(reviewDTO);
        }
    }

    private static final Date TWO_THOUSANDS_DATE = new Date(946681200000L);
}