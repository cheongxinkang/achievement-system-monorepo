package com.xk.template_service_as.controller;

import com.xk.template_service_as.dto.TemplateDTO;
import com.xk.template_service_as.entity.TemplateType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Controller
public class WelcomeController {


    public WelcomeController() {
        super();
    }

    @GetMapping("/")
    public String welcome(RedirectAttributes redirectAttributes) {
        return "redirect:/templates/create";
    }

}
