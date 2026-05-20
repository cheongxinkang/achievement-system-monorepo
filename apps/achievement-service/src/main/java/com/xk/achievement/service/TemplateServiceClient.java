package com.xk.achievement.service;

import com.xk.achievement.dto.TemplateDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
public class TemplateServiceClient {

    @Autowired
    private RestTemplate restTemplate;
    private final String TEMPLATE_SERVICE_URL = "http://localhost:8081/api/templates";

    public TemplateServiceClient() {

    }

    public List<TemplateDTO> getAllTemplates() {
        ResponseEntity<List<TemplateDTO>> response = restTemplate.exchange(
                TEMPLATE_SERVICE_URL,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<TemplateDTO>>() {}
        );
        return response.getBody();
    }

    public TemplateDTO getTemplateById(UUID id) {
        return restTemplate.getForObject(TEMPLATE_SERVICE_URL + "/" + id, TemplateDTO.class);
    }
}
