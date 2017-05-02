package com.andres_k.gameToolsLib.components.gameComponent.commands.comboComponent.combo;

import com.andres_k.custom.component.eventComponents.EInput;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.custom.component.gameComponent.commands.comboComponent.ECombos;

/**
 * Created by andres_k on 30/11/2015.
 */
public class ComboFactory {
    public static Combo createCombo(EGameObject object, ECombos type) {
        return null;
    }

    // GENERIC
    private static void addInfiniteElement(Combo combo, EInput input, EAnimation anim, int index, boolean change, int duration) {
        combo.addElement(new ComboElement(input, anim, index, change, duration));
        combo.addElement(new ComboElement(EInput.INFINITE, anim, index, change, duration));
    }

    private static void addInfiniteElement(Combo combo, EInput input, EAnimation animType, int animIndex, EAnimation hitType, int hitIndex, boolean change, int duration) {
        combo.addElement(new ComboElement(input, animType, animIndex, hitType, hitIndex, change, duration));
        combo.addElement(new ComboElement(EInput.INFINITE, animType, animIndex, hitType, hitIndex, change, duration));
    }

    private static void addElement(Combo combo, EInput input, EAnimation anim, int index, boolean change, int duration, int numbers) {
        for (int i = 0; i < numbers; ++i) {
            combo.addElement(new ComboElement(input, anim, index, change, duration));
        }
    }

    private static void addElement(Combo combo, EInput input, EAnimation animType, int animIndex, EAnimation hitType, int hitIndex, boolean change, int duration, int number) {
        for (int i = 0; i < number; ++i) {
            combo.addElement(new ComboElement(input, animType, animIndex, hitType, hitIndex, change, duration));
        }
    }
}
