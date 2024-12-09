package com.maisapires.todosimple.models.enums;

public enum ProfileEnum {
    ADMIN(1, "ROLE_ADMIN"),
    FUNCIONARIO(2, "ROLE_FUNCIONARIO");

    private int code;
    private String description;

    private ProfileEnum(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static ProfileEnum toEnum(Integer code) {
        if (code == null) {
            return null;
        }

        for (ProfileEnum x : ProfileEnum.values()) {
            if (code.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid ID: " + code);
    }
}