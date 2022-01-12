package com.aybu9.aybualumni.auth.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GreetingController {

    @Qualifier("sessionRegistry")
    private final SessionRegistry sessionRegistry;

    public GreetingController(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @GetMapping("/greet")
    public String hello() {
        return "Hello";
    }

    @GetMapping("/active-user-emails")
    public List<String> activeUserEmails() {
        final List<Object> allPrincipals = sessionRegistry.getAllPrincipals();
        List<String> activeUserEmails = new ArrayList<>();
        for(final Object principal : allPrincipals) {
            if(principal instanceof final String activeUserEmail) {
                activeUserEmails.add(activeUserEmail);
            }
        }
        return activeUserEmails;
    }


}
