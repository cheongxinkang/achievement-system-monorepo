package com.xk.template_service_as.entity.field;

import com.xk.template_service_as.dto.FieldDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class Field {

    protected String prompt;

    protected String variableName;

    protected FieldType type;

    @Override
    public String toString() {
        final ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(this);
    }

}
