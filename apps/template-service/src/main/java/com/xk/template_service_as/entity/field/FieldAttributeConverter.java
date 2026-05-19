package com.xk.template_service_as.entity.field;

import com.xk.template_service_as.service.FieldsParsingService;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.List;

@Converter
public class FieldAttributeConverter implements AttributeConverter<List<Field>, String> {

    private final FieldsParsingService fieldsParsingService;

    public FieldAttributeConverter(FieldsParsingService fieldsParsingService) {
        this.fieldsParsingService = fieldsParsingService;
    }

    @Override
    public String convertToDatabaseColumn(List<Field> fields) {
        return fieldsParsingService.toJson(fields);
    }

    @Override
    public List<Field> convertToEntityAttribute(String s) {
        return fieldsParsingService.toFieldList(s);
    }

}
