package com.andres_k.custom.component.gameComponent.gameObject.objects.entities;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.GameObjectController;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Obstacle;
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
            GameObjectController.get().getGameDesign().addWinner(enemy.getId());
            enemy.getAnimatorController().setDeleted(true);
        }
    }
}
