package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;

/**
 * Created by kevin on 25/04/2017.
 */
public class Trap extends Obstacle{
    public Trap(AnimatorController animatorController, EGameObject type, String id, float x, float y, float life, float damage) {
        super(animatorController, type, id, x, y, life, damage, 0, 0);
    }

    @Override
    public void manageDoHit(GameObject enemy) {
    }
}
