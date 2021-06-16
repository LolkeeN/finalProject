package com.epam.rd.fp.model.enums;

public enum Language {
    RU("ru"),
    EN("en");
    private String value;

    Language(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static Language fromString(String language) {
        if ("EN".equalsIgnoreCase(language)) {
            return Language.EN;
        }
        return Language.RU;
    }
}
