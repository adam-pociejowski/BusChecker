package com.valverde.buschecker.web.rest;

import com.valverde.buschecker.service.UserService2;
import com.valverde.buschecker.web.dto.AuthDTO;
import com.valverde.buschecker.web.dto.LoggedUser;
import com.valverde.buschecker.entity.User;
import com.valverde.buschecker.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private UserService2 userService2;

    @PostMapping("/authenticate")
    public ResponseEntity<Map<String, String>> authenticate(@RequestBody AuthDTO auth, HttpSession session)
            throws Exception {
        User user = userService2.getUserByUsername(auth.getUsername());
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

    @GetMapping("/loggeduser")
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

    @GetMapping("/logout")
    public ResponseEntity<Map<String, String>> logout(HttpSession session) {
        SecurityContext context = (SecurityContext) session.getAttribute("SPRING_SECURITY_CONTEXT");
        if (context != null) {
            session.removeAttribute("SPRING_SECURITY_CONTEXT");
        }
        return null;
    }
}