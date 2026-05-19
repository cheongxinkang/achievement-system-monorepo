package com.xk.template_service_as.controller;

import com.xk.template_service_as.dto.TemplateDTO;
import com.xk.template_service_as.service.TemplateService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/templates")
public class TemplateRestController {

    private final TemplateService templateService;

    public TemplateRestController(@Qualifier("v1TemplateService") TemplateService templateService) {
        this.templateService = templateService;
    }

    @GetMapping
    public ResponseEntity<List<TemplateDTO>> getAllTemplates() {
        return ResponseEntity.ok(templateService.getAllTemplates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TemplateDTO> getTemplateById(@PathVariable UUID id) {
        TemplateDTO template = templateService.getTemplateById(id);
        if (template != null) {
            return ResponseEntity.ok(template);
        }
        return ResponseEntity.notFound().build();
    }
}
