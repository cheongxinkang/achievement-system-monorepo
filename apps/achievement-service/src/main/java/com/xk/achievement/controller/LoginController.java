package com.xk.achievement.controller;

import com.xk.achievement.dto.TokenResponse;
import com.xk.achievement.service.AuthServiceClient;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private final AuthServiceClient authServiceClient;

    public LoginController(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @GetMapping("/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam String username, @RequestParam String password, HttpSession session, Model model) {
        try {
            TokenResponse response = authServiceClient.authenticate(username, password);
            if (response != null && response.access_token() != null) {
                session.setAttribute("JWT_TOKEN", response.access_token());
                return "redirect:/";
            }
        } catch (Exception e) {
            model.addAttribute("error", "Invalid username or password");
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @GetMapping("/register")
    public String showRegister() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            authServiceClient.register(username, password, "USER");
            return "redirect:/login?registered=true";
        } catch (Exception e) {
            model.addAttribute("error", "Registration failed. Username might be taken.");
            return "register";
        }
    }
}
