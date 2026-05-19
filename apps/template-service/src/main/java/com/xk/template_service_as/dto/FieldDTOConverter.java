package com.xk.template_service_as.dto;

import com.xk.template_service_as.service.FieldsParsingService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FieldDTOConverter implements Converter<String, List<FieldDTO>> {

    private final FieldsParsingService fieldsParsingService;

    public FieldDTOConverter(FieldsParsingService fieldsParsingService) {
        this.fieldsParsingService = fieldsParsingService;
    }

    @Override
    public List<FieldDTO> convert(String source) {
        return fieldsParsingService.toFieldDTOList(source);
    }
}
