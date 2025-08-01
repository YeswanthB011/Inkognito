package com.inkognito.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api")
public class LoginController {

    // Simple login endpoint that takes a name and stores it in session
    @PostMapping("/login")
    public String login(@RequestParam String name, HttpSession session) {
        if (name == null || name.trim().isEmpty()) {
            return "Name is required.";
        }
        session.setAttribute("userName", name.trim());
        return "Welcome, " + name + "!";
    }

    // Get the current user's name from session
    @GetMapping("/user")
    public String getUserName(HttpSession session) {
        Object name = session.getAttribute("userName");
        return name != null ? name.toString() : "No user logged in.";
    }
}