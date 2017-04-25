package com.andres_k.components.gameComponents.animations;


/**
 * Created by andres_k on 13/03/2015.
 */
public enum EAnimation {
    // STATE
    NULL(true),
    IDLE(true),
    EXPLODE(false),

    //BUTTON
    ON_FOCUS(true),
    ON_CLICK(true),

    // MOVE
    RUSH(false),
    RUN(true),
    JUMP(false),
    FALL(true),
    RECEIPT(false),

    // DEFENSE
    DEFENSE(false),
    BLOCK(false),
    TRANSPOSITION(false),

    // TOUCHED
    TOUCHED_SIMPLE(false),
    TOUCHED_MEDIUM(false),
    TOUCHED_PROPELS(false),
    TOUCHED_PROJECTED(false),
    TOUCHED_FLIP(false),
    TOUCHED_RECEIPT(false),
    TOUCHED_FALL(false),

    // ATTACK


    // FINAL
    WIN(true),
    LOSE(false);

    private boolean loop;

    EAnimation(boolean loop) {
        this.loop = loop;
    }

    public boolean isLoop() {
        return this.loop;
    }

    public static boolean checkLoop(EAnimation value) {
        EAnimation[] enums = EAnimation.values();
        for (EAnimation type : enums) {
            if (type.equals(value)) {
                return type.isLoop();
            }
        }
        return false;
    }
}
