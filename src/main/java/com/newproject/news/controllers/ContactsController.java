package com.newproject.news.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class ContactsController {

@GetMapping("/contact")
public String viewContactPage(Model model, HttpServletRequest request) {
    return "Contact_us";
}
}
