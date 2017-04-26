package com.andres_k.components.gameComponents.gameObject.objects.entities;

import com.andres_k.components.gameComponents.animations.AnimatorController;
import com.andres_k.components.gameComponents.animations.EAnimation;
import com.andres_k.components.gameComponents.gameObject.EGameObject;
import com.andres_k.components.gameComponents.gameObject.GameObject;
import com.andres_k.components.gameComponents.gameObject.GameObjectController;
import com.andres_k.components.gameComponents.gameObject.objects.Obstacle;
import org.newdawn.slick.SlickException;

/**
 * Created by kevin on 25/04/2017.
 */
public class Gate extends Obstacle {
    public Gate(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.GATE, id, x, y, 0, 0, 0, 0);
    }

    @Override
    public void manageDoHit(GameObject enemy) {
        if (enemy.getType() == EGameObject.SLIME && !enemy.getAnimatorController().isDeleted()) {
            try {
                this.getAnimatorController().changeAnimation(EAnimation.RUN);
            } catch (SlickException e) {
                e.printStackTrace();
            }
            GameObjectController.get().addWinner(enemy.getId());
            enemy.getAnimatorController().setDeleted(true);
        }
    }
}
