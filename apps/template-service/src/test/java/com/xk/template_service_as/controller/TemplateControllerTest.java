package com.xk.template_service_as.controller;

import com.xk.template_service_as.dto.FieldDTO;
import com.xk.template_service_as.dto.FieldRow;
import com.xk.template_service_as.entity.TemplateType;
import jakarta.servlet.ServletException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
public class TemplateControllerTest {

    private final WebApplicationContext context;
    private final ObjectMapper objectMapper;

    @Autowired
    TemplateControllerTest(WebApplicationContext context, ObjectMapper objectMapper) {
        this.context = context;
        this.objectMapper = objectMapper;
    }

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testGetCreateForm() throws Exception {
        mockMvc.perform(get("/templates/create"))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("templateDTO"))
            .andExpect(view().name("home"));
    }

    @Test
    void testAddFieldsToForm() throws Exception {
        FieldRow[] fieldRows = {
            new FieldRow(-1, "TEXT"),
            new FieldRow(-1, "NUMERICAL"),
            new FieldRow(-1, "DATE_TIME")
        };

        mockMvc.perform(
                post("/templates/update-fields")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(fieldRows)))
            .andExpect(status().isOk())
            .andExpect(model().attributeExists("templateDTO"))
            .andExpect(model().attribute("templateDTO", hasProperty("fields", hasSize(3))))
            .andExpect(view().name("home"));
    }

    @Test
    void testFailFieldAddition() {
        FieldRow[] fieldRows = { new FieldRow(-1, "ABCDEFGH")};

        Exception exception = assertThrows(ServletException.class, () -> {
            mockMvc.perform(
                post("/templates/update-fields")
                    .contentType("application/json")
                    .content(objectMapper.writeValueAsString(fieldRows)));
        });
        Assertions.assertEquals("Request processing failed: java.lang.RuntimeException: Field type not supported.", exception.getMessage());
    }

    @Test
    void testCreateFormSuccess() throws Exception {
        mockMvc.perform(
                post("/templates/create")
                    .param("templateName", "Test Template")
                    .param("type", String.valueOf(TemplateType.FORM))
                    .param("fields", fieldDTOs()))
            .andExpect(status().is3xxRedirection());
    }

    private String fieldDTOs() {
        List<FieldDTO> fieldDTOs = new ArrayList<>();

        fieldDTOs.add(FieldDTO.builder()
            .type("TEXT")
            .prompt("This is a question.")
            .variableName("testVar1")
            .build());

        return fieldDTOs.toString();
    }

}
