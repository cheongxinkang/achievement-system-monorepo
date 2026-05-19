package com.xk.template_service_as.entity.field;

public enum FieldType {
    TEXT, // uses text value
    NUMERICAL, // uses numerical value
    MULTIPLE_CHOICE, // uses multiple choice list value which will parse it as a list of strings
    DATE_TIME, // uses date time value
    DYNAMIC, // just the type, no need actual fields
    PROGRESSIVE, // just the type, no need actual fields
    CHECKBOX, // uses boolean value
    COUNT, // uses numerical value as the denominator, numerator is not necessary
    DEADLINE // shares date time value with date_time type
}
