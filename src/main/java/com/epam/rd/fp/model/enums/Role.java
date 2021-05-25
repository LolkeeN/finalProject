package com.epam.rd.fp.model.enums;

public enum Role {
    USER(1),
    MODERATOR(2),
    SPEAKER(3);
    private int value;

    Role(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
