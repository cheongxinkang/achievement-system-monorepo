package com.xk.template_service_as.entity;

import com.xk.template_service_as.entity.field.Field;
import com.xk.template_service_as.entity.field.TextField;
import com.xk.template_service_as.service.FieldsParsingService;
import com.xk.template_service_as.entity.field.FieldAttributeConverter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import tools.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.List;


public class FieldAttributeConverterTest {

    private final ObjectMapper objectMapper = new  ObjectMapper();
    private final FieldsParsingService fieldsParsingService = new FieldsParsingService(objectMapper);
    private final FieldAttributeConverter fieldAttributeConverter = new FieldAttributeConverter(fieldsParsingService);


    @Test
    void testConvertFieldsToString() {
        List<Field> fieldsList = createSampleFields();

        String json = fieldAttributeConverter.convertToDatabaseColumn(fieldsList);

        Assertions.assertEquals(sampleFieldsJson(), json);
    }

    @Test
    void testConvertJsonToFields() {
        List<Field> fieldsList = fieldAttributeConverter.convertToEntityAttribute(sampleFieldsJson());

        Assertions.assertEquals(createSampleFields(), fieldsList);
    }

    public static List<Field> createSampleFields() {
        List<Field> fieldsList = new ArrayList<>();
        fieldsList.add(new TextField("stayGold", "Do you know Stay Gold?", ""));
        fieldsList.add(new TextField("inTheEnd", "Do you know Linkin Park?", ""));

        return fieldsList;
    }

    public static String sampleFieldsJson() {
        return "[{\"prompt\":\"stayGold\",\"variableName\":\"Do you know Stay Gold?\",\"textValue\":\"\",\"type\":\"TEXT\"},{\"prompt\":\"inTheEnd\",\"variableName\":\"Do you know Linkin Park?\",\"textValue\":\"\",\"type\":\"TEXT\"}]";
    }

}
