package com.xk.template_service_as.service;

import com.xk.template_service_as.dto.FieldDTO;
import com.xk.template_service_as.dto.FieldRow;
import com.xk.template_service_as.dto.TemplateConverter;
import com.xk.template_service_as.dto.TemplateDTO;
import com.xk.template_service_as.entity.field.FieldType;
import com.xk.template_service_as.entity.Template;
import com.xk.template_service_as.entity.field.TextField;
import com.xk.template_service_as.repository.TemplateRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Service
@Qualifier("v1TemplateService")
public class TemplateServiceImpl implements TemplateService {

    private final TemplateRepository templateRepository;
    private final TemplateConverter templateConverter;

    public TemplateServiceImpl(TemplateRepository templateRepository, TemplateConverter templateConverter) {
        this.templateRepository = templateRepository;
        this.templateConverter = templateConverter;
    }

    @Override
    public TemplateDTO createEmptyTemplate() {
        return TemplateDTO.builder()
                .fields(new ArrayList<>())
                .build();
    }

    @Override
    public TemplateDTO saveTemplate(TemplateDTO templateDTO) {
        Template savedTemplate = templateRepository.save(templateConverter.toEntity(templateDTO));
        return templateConverter.toDTO(savedTemplate);
    }

    @Override
    public List<TemplateDTO> getAllTemplates() {
        return templateRepository.findAll().stream().map(templateConverter::toDTO).toList();
    }

    @Override
    public TemplateDTO getTemplateById(java.util.UUID id) {
        return templateRepository.findById(id).map(templateConverter::toDTO).orElse(null);
    }

    @Override
    public TemplateDTO createOrReplaceFields(TemplateDTO templateDTO, FieldRow[] fieldRows) {
        List<FieldDTO> fieldDTOs = new ArrayList<>();

        for (var row : fieldRows) {
            fieldDTOs.add(createFieldDTO(row));
        }

        templateDTO.setFields(fieldDTOs);

        return templateDTO;
    }

    private FieldDTO createFieldDTO(FieldRow row) {
        String type = row.type();
        var fieldDTO = FieldDTO.builder();

        switch (type) {
            case "TEXT":
                fieldDTO.type(FieldType.TEXT.toString());
                break;
            case "NUMERICAL":
                fieldDTO.type(FieldType.NUMERICAL.toString());
                break;
            default:
                throw new RuntimeException("Field type not supported.");
        }

        return fieldDTO.build();
    }

}
