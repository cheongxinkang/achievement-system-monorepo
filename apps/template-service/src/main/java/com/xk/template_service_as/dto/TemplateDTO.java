package com.xk.template_service_as.dto;

import com.xk.template_service_as.entity.TemplateType;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Builder
@Data
public class TemplateDTO {

    private UUID id;

//    @NotEmpty(message = "Template needs to be named.")
    private String templateName;

//    @NotEmpty(message = "Template needs to be typed.")
    private TemplateType type;

//    @NotEmpty(message = "An empty template is useless.")
    private List<FieldDTO> fields;

    @PastOrPresent
    private LocalDateTime createdAt;

    @FutureOrPresent
    private LocalDateTime modifiedAt;

}

