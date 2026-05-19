package com.xk.template_service_as.dto;

import com.xk.template_service_as.entity.field.Field;
import com.xk.template_service_as.entity.Template;
import com.xk.template_service_as.service.FieldsParsingService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TemplateConverter {

    private final FieldsParsingService fieldsParsingService;

    public TemplateConverter(FieldsParsingService fieldsParsingService) {
        this.fieldsParsingService = fieldsParsingService;
    }

    public Template toEntity(TemplateDTO dto) {
        List<Field> fields = fieldsParsingService.toFieldList(dto.getFields());
        return Template.builder()
            .id(dto.getId())
            .templateName(dto.getTemplateName())
            .type(dto.getType())
            .fields(fields)
            .createdAt(dto.getCreatedAt())
            .modifiedAt(dto.getModifiedAt())
            .build();
    }

    public TemplateDTO toDTO(Template entity) {
        List<FieldDTO> fieldDTOs = fieldsParsingService.toFieldDtoList(entity.getFields());
        return TemplateDTO.builder()
            .id(entity.getId())
            .templateName(entity.getTemplateName())
            .type(entity.getType())
            .fields(fieldDTOs)
            .createdAt(entity.getCreatedAt())
            .modifiedAt(entity.getModifiedAt())
            .build();
    }

}
