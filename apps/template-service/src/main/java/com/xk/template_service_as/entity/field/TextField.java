package com.xk.template_service_as.entity.field;

import lombok.*;
import tools.jackson.databind.ObjectMapper;

@EqualsAndHashCode(callSuper = true)
public class TextField extends Field {

    @Getter @Setter
    String textValue;

    // Empty constructor necessary for ObjectMapper
    public TextField() {
        super("", "", FieldType.TEXT);
    }

    public TextField(String prompt, String variableName, String data) {
        super(prompt, variableName, FieldType.TEXT);
        this.textValue = data;
    }

}
