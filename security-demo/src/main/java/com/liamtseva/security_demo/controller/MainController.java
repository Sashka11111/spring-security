package com.liamtseva.security_demo.controller;

import com.liamtseva.security_demo.entity.User;
import com.liamtseva.security_demo.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MainController {

  @Autowired
  private UserDetailsServiceImpl userDetailsService;

  // Відображення форми реєстрації
  @GetMapping("/register")
  public String showRegistrationForm(Model model) {
    model.addAttribute("user", new User());
    return "register";
  }
  // Відображення форми входу
  @GetMapping("/login")
  public String showLoginForm(@RequestParam(value = "error", required = false) String error, Model model) {
    if (error != null) {
      model.addAttribute("error", "Невірний логін або пароль.");
    }
    return "login";
  }
  @GetMapping("/userHome")
  public String userHome() {
    return "userHome";
  }

  // Головна сторінка
  @GetMapping("/")
  public String home(@AuthenticationPrincipal UserDetails userDetails, Model model) {
    if (userDetails != null) {
      model.addAttribute("username", userDetails.getUsername());
    }
    return "home";
  }
}
