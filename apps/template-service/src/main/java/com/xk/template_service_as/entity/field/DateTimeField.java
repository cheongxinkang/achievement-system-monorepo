package com.xk.template_service_as.entity.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
public class DateTimeField extends Field {

    @JsonProperty("dateTimeValue") @Getter @Setter
    LocalDateTime dateTime;

    // Empty constructor necessary for ObjectMapper
    public DateTimeField() {
        super("", "", FieldType.DATE_TIME);
    }

    public DateTimeField(String prompt, String variableName, LocalDateTime data) {
        super(prompt, variableName, FieldType.DATE_TIME);
        this.dateTime = data;
    }

}
