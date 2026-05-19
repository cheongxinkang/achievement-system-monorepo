package com.xk.template_service_as.entity.field;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@EqualsAndHashCode(callSuper = true)
public class CheckBoxField extends Field {

    @JsonProperty("booleanValue")
    @Getter
    @Setter
    boolean isChecked;

    public CheckBoxField() {
        super("", "", FieldType.CHECKBOX);
    }

    public CheckBoxField(String prompt, String variableName, boolean data) {
        super(prompt, variableName, FieldType.CHECKBOX);
        this.isChecked = data;
    }

}
