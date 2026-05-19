package com.xk.template_service_as.entity.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
public class NumericalField extends Field {

    @JsonProperty("numberValue")
    @Getter
    @Setter
    double realNumber;

    // Empty constructor necessary for ObjectMapper
    public NumericalField() {
        super("", "", FieldType.NUMERICAL);
    }

    @JsonCreator
    public NumericalField(
        @JsonProperty("prompt") String prompt,
        @JsonProperty("variableName") String variableName,
        @JsonProperty("numberValue") double data
    ) {
        super(prompt, variableName, FieldType.NUMERICAL);
        this.realNumber = data;
    }

}
