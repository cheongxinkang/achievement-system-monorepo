package com.xk.template_service_as.entity;

import lombok.Getter;

@Getter
public enum TemplateType {
    FORM("FORM");

    public static final TemplateType[] ALL = { FORM };

    private final String name;



    public static TemplateType forName(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Name cannot be null for type");
        }
        switch(name.toUpperCase()) {
            case "FORM":
                return FORM;
        }
        throw new IllegalArgumentException("Name \"" + name + "\" does not correspond to any Type");
    }

    private TemplateType(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
