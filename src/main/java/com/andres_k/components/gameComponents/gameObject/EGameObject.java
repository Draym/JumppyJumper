package com.andres_k.components.gameComponents.gameObject;

/**
 * Created by andres_k on 13/03/2015.
 */
public enum EGameObject {
    /* Admin */
    NULL("NULL"),

    // primary
    UNBREAKABLE("UNBREAKABLE"),
    SOLID("SOLID"),
    FILMY("FILMY"),
    SPECIAL("SPECIAL"),

    //collisions
    ATTACK_BODY("attackBody", FILMY.getValue()),
    DEFENSE_BODY("defenseBody", SOLID.getValue()),
    BLOCK_BODY("blockBody", UNBREAKABLE.getValue()),

    //types
    ANIMATED("ANIMATED", SOLID.getValue()),
    DEADPAN("DEADPAN", UNBREAKABLE.getValue()),
    PLATFORM("PLATFORM", DEADPAN.getValue()),
    BORDER("BORDER", DEADPAN.getValue()),
    PLAYER("PLAYER", ANIMATED.getValue()),
    ENTITY("ENTITY", ANIMATED.getValue()),
    BONUS("BONUS", SPECIAL.getValue()),
    TRAP("TRAP", UNBREAKABLE.getValue()),

    //items
    MAP("MAP", UNBREAKABLE.getValue()),
    GROUND("GROUND", PLATFORM.getValue()),
    WALL("WALL", BORDER.getValue()),
    PORTAL("PORTAL", SPECIAL.getValue()),
    COIN("COIN", BONUS.getValue()),
    HEART("HEART", BONUS.getValue()),
    GATE("GATE", SPECIAL.getValue()),
    FIRE_GUN("FIRE_GUN", TRAP.getValue()),
    STEEL_WHEEL("STEEL_WHEEL", TRAP.getValue()),
    STEEL_PIKE("STEEL_PIKE", TRAP.getValue()),

    //players
    SLIME("SLIME", PLAYER.getValue());


    private final String value;
    private final String type;
    private final boolean available;

    EGameObject(String value) {
        this.value = value;
        this.type = value;
        this.available = true;
    }

    EGameObject(String value, String type) {
        this.value = value;
        this.type = type;
        this.available = true;
    }

    EGameObject(String value, String type, boolean available) {
        this.value = value;
        this.type = type;
        this.available = available;
    }

    public String getValue() {
        return value;
    }

    public String getType() {
        return this.type;
    }

    public static EGameObject getEnumByValue(String value) {
        EGameObject[] enums = EGameObject.values();
        for (EGameObject type : enums) {
            if (type.getValue().equals(value))
                return type;
        }
        return NULL;
    }

    public static EGameObject getEnumByType(String value) {
        EGameObject[] enums = EGameObject.values();
        for (EGameObject type : enums) {
            if (value.equals(type.getValue())) {
                return type;
            }
        }
        return NULL;
    }

    public boolean isIn(EGameObject dir) {
        EGameObject current = EGameObject.getEnumByValue(this.value);

        while (!current.getValue().equals(current.getType())) {
            if (current == dir) {
                return true;
            } else {
                current = EGameObject.getEnumByType(current.getType());
            }
        }
        return (current == dir);
    }

    public boolean isAvailable() {
        return this.available;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
