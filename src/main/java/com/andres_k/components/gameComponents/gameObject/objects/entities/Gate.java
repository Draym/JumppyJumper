package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;

/**
 * Created by kevin on 25/04/2017.
 */
public class Gate extends Obstacle {
    public Gate(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.GATE, id, x, y, 0, 0, 0, 0);
    }

    @Override
    public void manageDoHit(GameObject enemy) {
    }
}
