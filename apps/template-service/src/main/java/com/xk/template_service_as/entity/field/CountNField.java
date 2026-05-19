package com.xk.template_service_as.entity.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
public class CountNField extends Field {

    @JsonProperty("numberValue")
    @Getter
    @Setter
    int denominator;

    public CountNField() {
        super("", "", FieldType.COUNT);
    }

    @JsonCreator
    public CountNField(
        @JsonProperty("prompt") String prompt,
        @JsonProperty("variableName") String variableName,
        @JsonProperty("numberValue") int data
    ) {
        super(prompt, variableName, FieldType.COUNT);
        this.denominator = data;
    }


}
