package com.xk.template_service_as.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import tools.jackson.databind.ObjectMapper;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FieldDTO {

    private String type;
    private String prompt;
    private String variableName;
    private String textValue;
    private String numberValue;
    private String booleanValue;
    private String dateTimeValue;
    private String multipleChoiceListValues;

    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }

}
