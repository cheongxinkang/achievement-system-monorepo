package com.xk.template_service_as.entity.field;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
public class MultipleChoiceField extends Field {

    @JsonProperty("textValue")
    @Getter
    @Setter
    List<String> choices;

    public MultipleChoiceField() {
        super("", "", FieldType.MULTIPLE_CHOICE);
    }

    @JsonCreator
    public MultipleChoiceField(
        @JsonProperty("prompt") String prompt,
        @JsonProperty("variableName") String variableName,
        @JsonProperty("textValue") String data
    ) {
        super(prompt, variableName, FieldType.MULTIPLE_CHOICE);
        // process data by delimiting it and creating list of string
    }

}
