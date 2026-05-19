package com.xk.template_service_as.entity;

import com.xk.template_service_as.entity.field.Field;
import com.xk.template_service_as.entity.field.FieldAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="templates")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Template {

    @Id @GeneratedValue
    private UUID id;

    @Column
    private String templateName;

    @Column
    private TemplateType type;

    @Convert(converter = FieldAttributeConverter.class)
    private List<Field> fields;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime modifiedAt;

}
