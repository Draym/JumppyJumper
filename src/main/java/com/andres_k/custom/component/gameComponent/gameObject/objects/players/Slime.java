package com.andres_k.custom.component.gameComponent.gameObject.objects.players;

import com.andres_k.gameToolsLib.components.gameComponent.animations.AnimatorController;
import com.andres_k.custom.component.gameComponent.animation.EAnimation;
import com.andres_k.custom.component.gameComponent.gameObject.EGameObject;
import com.andres_k.gameToolsLib.components.gameComponent.gameObject.objects.Character;
import com.andres_k.gameToolsLib.components.gameComponent.movement.EDirection;
import com.andres_k.gameToolsLib.utils.configs.GameConfig;
import org.newdawn.slick.SlickException;

/**
 * Created by andres_k on 13/10/2015.
 */
public class Slime extends Character {

    public Slime(AnimatorController animatorController, String id, float x, float y) {
        super(animatorController, EGameObject.SLIME, id, x, y, 1, 0, 100, 220, 15);
        try {
            this.animatorController.setEyesDirection(EDirection.RIGHT);
            this.animatorController.changeAnimation(EAnimation.RUN);
            this.movement.setMoveDirection(EDirection.RIGHT);
            this.movement.setPushX(GameConfig.speedTravel);
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
