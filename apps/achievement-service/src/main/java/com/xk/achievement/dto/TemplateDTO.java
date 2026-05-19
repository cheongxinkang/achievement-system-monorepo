package com.xk.achievement.dto;

import java.util.List;
import java.util.UUID;

public class TemplateDTO {
    private UUID id;
    private String templateName;
    private String type;
    private List<FieldDTO> fields;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getTemplateName() { return templateName; }
    public void setTemplateName(String templateName) { this.templateName = templateName; }
    
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    
    public List<FieldDTO> getFields() { return fields; }
    public void setFields(List<FieldDTO> fields) { this.fields = fields; }
}
