package com.andres_k.components.eventComponent.input;

/**
 * Created by andres_k on 16/03/2015.
 */
public enum EInput {
    // TYPE
    NOTHING("NOTHING"),
    INFINITE("INFINITE"),
    RELEASED("RELEASED"), PRESSED("PRESSED"),
    KEY_RELEASED("KEY" + RELEASED.value), KEY_PRESSED("KEY" + PRESSED.value),
    MOUSE_RELEASED("MOUSE" + RELEASED.value), MOUSE_PRESSED("MOUSE" + PRESSED.value),

    // ACTIONS
    PORTAL_CHANGE("PORTAL_CHANGE");


    private final int index;
    private final String value;

    EInput(String value) {
        this.index = -1;
        this.value = value;
    }

    EInput(int index, String value) {
        this.index = index;
        this.value = value;
    }

    public String getValue() {
        return this.value;
    }

    public int getIndex() {
        return this.index;
    }

    public static EInput getEnumByIndex(int index) {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (index == type.getIndex()) {
                return type;
            }
        }
        return NOTHING;
    }

    public static EInput getEnumByValue(String value) {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (type.getValue().equals(value)) {
                return type;
            }
        }
        return NOTHING;
    }

    public static int getIndexByValue(String value) {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (type.getValue().equals(value)) {
                return type.getIndex();
            }
        }
        return NOTHING.getIndex();
    }

    public boolean isIn(EInput input) {
        return this.getValue().contains(input.getValue());
    }

    public EInput getContainer() {
        EInput[] enums = EInput.values();

        for (EInput type : enums) {
            if (this.isIn(type)) {
                return type;
            }
        }
        return NOTHING;
    }

    @Override
    public String toString() {
        return this.getValue();
    }
}
