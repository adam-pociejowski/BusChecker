package com.valverde.buschecker.rest;

import com.valverde.buschecker.dto.CheckerDTO;
import com.valverde.buschecker.dto.PersonDTO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class CheckerRestController {

    @RequestMapping(value = "/secured/rest/checkerdata")
    public CheckerDTO getCheckerData() {
        CheckerDTO dto = new CheckerDTO();

        PersonDTO driver = new PersonDTO();
        driver.setFirstname("Roman");
        driver.setLastname("Pociejowski");
        dto.setDriver(driver);
        dto.setBusName("Mercedes");
        dto.setSideNumber("1503");

        List<PersonDTO> sitters = new ArrayList<>();
        PersonDTO sitter = new PersonDTO();
        sitter.setFirstname("Mażena");
        sitter.setLastname("Dziczkowska");
        sitters.add(sitter);
        dto.setBabySitters(sitters);
        dto.setNumberOfSeats(23);
        dto.setExtinguisherReviewDate(new Date());
        dto.setInsuranceDate(new Date());
        dto.setTechnicalReviewDate(new Date());
        dto.setLiftReviewDate(new Date());
        dto.setTachographReviewDate(new Date());
        dto.setNotificationBetweenEventDays(14);
        return dto;
    }
}
