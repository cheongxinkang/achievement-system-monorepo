package com.xk.template_service_as.repository;

import com.xk.template_service_as.entity.Template;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface TemplateRepository extends JpaRepository<Template, UUID> {
    List<Template> getAllByTemplateName(String templateName, Sort sort, Limit limit);}
