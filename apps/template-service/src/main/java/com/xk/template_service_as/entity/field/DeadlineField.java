package com.xk.template_service_as.entity.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
public class DeadlineField extends Field {

    @JsonProperty("dateTimeValue") @Getter @Setter
    LocalDateTime deadline;

    public DeadlineField() {
        super("", "", FieldType.DEADLINE);
    }

    public DeadlineField(String prompt, String variableName, LocalDateTime data) {
        super(prompt, variableName, FieldType.DEADLINE);
        this.deadline = data;
    }

}
