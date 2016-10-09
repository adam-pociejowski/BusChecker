package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.web.dto.AuthDTO;
import com.valverde.buschecker.web.dto.LoggedUser;
import com.valverde.buschecker.web.dto.RegisterDTO;
import com.valverde.buschecker.entity.BusDriver;
import com.valverde.buschecker.entity.Sitter;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import com.valverde.buschecker.util.DateUtils;
import com.valverde.buschecker.util.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.*;

@RestController
public class AuthRestController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthDTO auth, HttpSession session) {
        User user = userService.getUser(auth.getUsername());
        if (user != null) {
            String password = user.getPassword();
            if (password.equals(auth.getPassword())) {
                Map<String, String> map = new HashMap<>();
                map.put("username", user.getUsername());
                map.put("role", "USER_ROLE");
                List<GrantedAuthority> authorities = new ArrayList<>();
                authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
                UserDetails userDetails = new LoggedUser(user.getUsername(), user.getPassword(), authorities);

                Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, authorities);
                SecurityContext context = SecurityContextHolder.getContext();
                context.setAuthentication(authentication);
                session.setAttribute("SPRING_SECURITY_CONTEXT", context);
                return new ResponseEntity<>(map, HttpStatus.ACCEPTED);
            }
        }
        return null;
    }

    @RequestMapping(value = "/loggeduser", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> getLoggedUser(HttpSession session) {

        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context != null) {
            Authentication authentication = context.getAuthentication();
            LoggedUser user = (LoggedUser) authentication.getPrincipal();
            List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>(authentication.getAuthorities());
            System.out.println(authorities.toString()+" "+user.getUsername());
            Map<String, String> map = new HashMap<>();
            map.put("username", user.getUsername());
            map.put("role", authorities.get(0).toString());
            return new ResponseEntity<>(map, HttpStatus.OK);
        }
        return null;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context != null) {
            session.removeAttribute("SPRING_SECURITY_CONTEXT");
        }
        return null;
    }

    @RequestMapping(value = "/registerUser", method = RequestMethod.POST)
    public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDTO dto) {
        User registeredUser = getUserFromRegisterDTO(dto);
        Boolean success = userService.saveUser(registeredUser);
        if (success) {
            System.out.println("success");

        }
        else {
            System.out.println("fail");
        }
        return null;
    }

    private User getUserFromRegisterDTO(RegisterDTO dto) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(dto.getPassword());
        user.setNotificationBetweenEventDays(dto.getNotificationBetweenEventDays());

        BusDriver busDriver = new BusDriver();
        busDriver.setFirstname(dto.getFirstname());
        busDriver.setLastname(dto.getLastname());
        busDriver.setSideNumber(dto.getSideNumber());
        busDriver.setBusName(dto.getBusName());
        busDriver.setRejestrNumber(dto.getRejestrNumber());
        busDriver.setNumberOfSeats(dto.getNumberOfSeats());
        busDriver.setExtinguisherReviewDate(DateUtils.stringToDate(dto.getExtinguisherReviewDate(), "dd/MM/yyyy"));
        busDriver.setInsuranceDate(DateUtils.stringToDate(dto.getInsuranceDate(), "dd/MM/yyyy"));
        busDriver.setTechnicalReviewDate(DateUtils.stringToDate(dto.getTechnicalReviewDate(), "dd/MM/yyyy"));
        busDriver.setTachographReviewDate(DateUtils.stringToDate(dto.getTachographReviewDate(), "dd/MM/yyyy"));
        busDriver.setLiftReviewDate(DateUtils.stringToDate(dto.getLiftReviewDate(), "dd/MM/yyyy"));

        List<Sitter> sitters = new ArrayList<>();
        busDriver.setSitters(UserUtils.sitterDTOToEntity(sitters, dto.getSitters(), busDriver));

        List<BusDriver> busses = new ArrayList<>();
        busses.add(busDriver);
        user.setBuses(busses);
        return user;
    }
}