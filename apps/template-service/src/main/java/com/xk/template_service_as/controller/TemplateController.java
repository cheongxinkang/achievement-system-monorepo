package com.xk.template_service_as.controller;

import com.xk.template_service_as.dto.FieldRow;
import com.xk.template_service_as.dto.TemplateDTO;
import com.xk.template_service_as.entity.TemplateType;
import com.xk.template_service_as.service.TemplateService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/templates")
@Validated
public class TemplateController {

    private static final String TEMPLATE_FORM = "home";

    TemplateService templateService;

    @ModelAttribute("allTypes")
    public List<TemplateType> populateTypes() {
        return Arrays.asList(TemplateType.ALL);
    }

    @ModelAttribute("templateDTO")
    public TemplateDTO createBlankTemplate() {
        return templateService.createEmptyTemplate();
    }


    public TemplateController(
        @Qualifier("v1TemplateService") TemplateService templateService) {
        this.templateService = templateService;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id", "createdAt", "modifiedAt");
    }


    @GetMapping("/create")
    public String getForm() {
        return TEMPLATE_FORM;
    }


    @PostMapping("/create")
    public String createTemplate(@Valid TemplateDTO templateDTO, BindingResult result, RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "There was an error in creating the template.");
            return TEMPLATE_FORM;
        }

        templateDTO = templateService.saveTemplate(templateDTO);
        redirectAttributes.addFlashAttribute("message", "New Template created.");

        return "redirect:/templates/" + templateDTO.getId();
    }


    @PostMapping("/update-fields")
    public String updateTemplateFields(@RequestBody FieldRow[] fieldRowArray, BindingResult result, TemplateDTO templateDTO) {
        templateService.createOrReplaceFields(templateDTO, fieldRowArray);
        return TEMPLATE_FORM;
    }

}
